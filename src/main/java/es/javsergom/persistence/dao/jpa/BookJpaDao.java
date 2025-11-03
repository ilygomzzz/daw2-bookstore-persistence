package es.javsergom.persistence.dao.jpa;

import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.Optional;

public interface BookJpaDao extends GenericJpaDao<BookJpaEntity>{
    Optional<BookJpaEntity> findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
