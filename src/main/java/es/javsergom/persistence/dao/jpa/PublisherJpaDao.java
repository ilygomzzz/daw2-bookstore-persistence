package es.javsergom.persistence.dao.jpa;

import es.javsergom.persistence.dao.jpa.entity.PublisherJpaEntity;

import java.util.Optional;

public interface PublisherJpaDao extends GenericJpaDao<PublisherJpaEntity>{
    Optional<PublisherJpaEntity> findBySlug(String slug);
    void deleteBySlug(String slug);
}
