package es.javsergom.persistence.dao.jpa.impl;

import es.javierserrano.domain.exception.ResourceNotFoundException;
import es.javsergom.persistence.dao.jpa.BookJpaDao;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class BookJpaDaoImpl implements BookJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BookJpaEntity> findByIsbn(String isbn) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookJpaEntity> criteria = builder.createQuery(BookJpaEntity.class);
        Root<BookJpaEntity> root = criteria.from(BookJpaEntity.class);

        criteria.select(builder.construct(BookJpaEntity.class)).where(builder.equal(root.get("isbn"), isbn));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByIsbn(String isbn) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<BookJpaEntity> criteria = builder.createCriteriaDelete(BookJpaEntity.class);
        Root<BookJpaEntity> root = criteria.from(BookJpaEntity.class);

        criteria.where(builder.equal(root.get("isbn"), isbn));
        entityManager.createQuery(criteria).executeUpdate();
    }

    @Override
    public List<BookJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "select a from BookJpaEntity a";
        TypedQuery<BookJpaEntity> query = entityManager.createQuery(sql, BookJpaEntity.class)
                .setFirstResult(pageIndex * pageIndex)
                .setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Optional<BookJpaEntity> findById(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookJpaEntity> criteria = builder.createQuery(BookJpaEntity.class);
        Root<BookJpaEntity> root = criteria.from(BookJpaEntity.class);

        criteria.select(builder.construct(BookJpaEntity.class)).where(builder.equal(root.get("id"), id));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteria).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public BookJpaEntity insert(BookJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public BookJpaEntity update(BookJpaEntity entity) {
        BookJpaEntity managedEntity = entityManager.find(BookJpaEntity.class, entity.getId());
        if (managedEntity == null) {
            throw new ResourceNotFoundException("Book with id " + entity.getId() + " not found");
        }

        entityManager.flush();
        return entityManager.merge(managedEntity);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(BookJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("select count(a) from BookJpaEntity a", Long.class).getSingleResult();
    }
}
