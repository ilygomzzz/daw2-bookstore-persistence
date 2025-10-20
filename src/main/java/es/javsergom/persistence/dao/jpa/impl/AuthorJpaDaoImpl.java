package es.javsergom.persistence.dao.jpa.impl;

import es.javierserrano.domain.exception.ResourceNotFoundException;
import es.javsergom.persistence.dao.jpa.AuthorJpaDao;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AuthorJpaDaoImpl implements AuthorJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorJpaEntity> findAll(int page, int size) {
        int pageIndex = Math.max(page - 1, 0);

        String sql = "SELECT a FROM AuthorJpaEntity a ORDER BY a.id";
        TypedQuery<AuthorJpaEntity> authorJpaEntityPage =
                entityManager.createQuery(sql, AuthorJpaEntity.class)
                        .setFirstResult(pageIndex * size)
                        .setMaxResults(size);

        return authorJpaEntityPage.getResultList();
    }

    @Override
    public Optional<AuthorJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorJpaEntity.class, id));
    }

    @Override
    public AuthorJpaEntity insert(AuthorJpaEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public AuthorJpaEntity update(AuthorJpaEntity entity) {
        AuthorJpaEntity mangaedEntity = entityManager.find(AuthorJpaEntity.class, entity.getId());
        if(mangaedEntity == null) {
            throw new ResourceNotFoundException("Author with id " + entity.getId() + " not found");
        }

        entityManager.flush();
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(AuthorJpaEntity.class, id));
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM AuthorJpaEntity a", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<AuthorJpaEntity> findBySlug(String slug) {
        String sql = "SELECT a FROM AuthorJpaEntity a WHERE a.slug = :slug";
        try {
            return Optional.of(entityManager.createQuery(sql, AuthorJpaEntity.class)
                    .setParameter("slug", slug)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteBySlug(String slug) {
        entityManager.createQuery("DELETE FROM AuthorJpaEntity a WHERE a.slug = :slug")
                .setParameter("slug", slug)
                .executeUpdate();
    }
}
