package es.javsergom.persistence.dao.jpa;

import es.javierserrano.domain.repository.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorJpaDao extends GenericJpaDao<AuthorEntity> {
    Optional<AuthorEntity> findBySlug(String slug);
    void deleteBySlug(String slug);
}
