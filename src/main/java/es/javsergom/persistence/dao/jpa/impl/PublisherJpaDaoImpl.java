package es.javsergom.persistence.dao.jpa.impl;

import es.javierserrano.domain.exception.ResourceNotFoundException;
import es.javsergom.persistence.dao.jpa.PublisherJpaDao;
import es.javsergom.persistence.dao.jpa.entity.PublisherJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class PublisherJpaDaoImpl implements PublisherJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PublisherJpaEntity> findBySlug(String slug) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublisherJpaEntity> criteriaQuery = builder.createQuery(PublisherJpaEntity.class);
        Root<PublisherJpaEntity> root = criteriaQuery.from(PublisherJpaEntity.class);
        criteriaQuery.select(builder.construct(PublisherJpaEntity.class)).where(builder.equal(root.get("slug"), slug));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteBySlug(String slug) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<PublisherJpaEntity> criteriaDelete = builder.createCriteriaDelete(PublisherJpaEntity.class);
        Root<PublisherJpaEntity> root = criteriaDelete.from(PublisherJpaEntity.class);

        criteriaDelete.where(builder.equal(root.get("slug"), slug));
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public List<PublisherJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublisherJpaEntity> criteriaQuery = builder.createQuery(PublisherJpaEntity.class);
        Root<PublisherJpaEntity> root = criteriaQuery.from(PublisherJpaEntity.class);
        criteriaQuery.select(root).orderBy(builder.asc(root.get("id")));

        TypedQuery<PublisherJpaEntity> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageIndex * size)
                .setMaxResults(size);

        return typedQuery.getResultList();

    }

    @Override
    public Optional<PublisherJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(PublisherJpaEntity.class, id));
    }

    @Override
    public PublisherJpaEntity insert(PublisherJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public PublisherJpaEntity update(PublisherJpaEntity entity) {
        PublisherJpaEntity managedEntity = entityManager.find(PublisherJpaEntity.class, entity.getId());
        if (managedEntity == null) {
            throw new ResourceNotFoundException("Publisher with id " + entity.getId() + " not found");
        }

        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(PublisherJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM AuthorJpaEntity a", Long.class)
                .getSingleResult();
    }
}
