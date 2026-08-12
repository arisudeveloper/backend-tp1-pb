package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.RestauranteHistoricoResponse;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricoService {

    private final EntityManager entityManager;

    public HistoricoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<RestauranteHistoricoResponse> historicoDoRestaurante(Long restauranteId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);

        @SuppressWarnings("unchecked")
        List<Object[]> revisoes = reader.createQuery()
                .forRevisionsOfEntity(Restaurante.class, false, true)
                .add(AuditEntity.id().eq(restauranteId))
                .getResultList();

        List<RestauranteHistoricoResponse> historico = new ArrayList<>();
        for (Object[] linha : revisoes) {
            Restaurante snapshot = (Restaurante) linha[0];
            DefaultRevisionEntity revInfo = (DefaultRevisionEntity) linha[1];
            RevisionType tipo = (RevisionType) linha[2];

            RestauranteHistoricoResponse dto = new RestauranteHistoricoResponse();
            dto.setRevisao(revInfo.getId());
            dto.setDataHora(LocalDateTime.ofInstant(
                    revInfo.getRevisionDate().toInstant(), ZoneId.systemDefault()));
            dto.setTipoOperacao(traduzir(tipo));

            if (snapshot != null) {
                dto.setId(snapshot.getId());
                dto.setNome(snapshot.getNome());
                dto.setCozinha(snapshot.getCozinha());
                dto.setAtivo(snapshot.getAtivo());
            }
            historico.add(dto);
        }
        return historico;
    }

    private String traduzir(RevisionType tipo) {
        return switch (tipo) {
            case ADD -> "CRIADO";
            case MOD -> "ATUALIZADO";
            case DEL -> "REMOVIDO";
        };
    }
}