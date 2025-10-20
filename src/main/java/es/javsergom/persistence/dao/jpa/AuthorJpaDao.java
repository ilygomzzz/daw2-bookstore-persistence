package es.javsergom.persistence.dao.jpa;

import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorJpaDao extends GenericJpaDao<AuthorJpaEntity> {
    Optional<AuthorJpaEntity> findBySlug(String slug);
    void deleteBySlug(String slug);
}
