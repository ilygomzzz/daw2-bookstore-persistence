package es.javsergom.persistence.repository;

import es.javierserrano.domain.mapper.AuthorMapper;
import es.javierserrano.domain.repository.AuthorRepository;
import es.javierserrano.domain.repository.entity.AuthorEntity;
import es.javsergom.persistence.dao.jpa.AuthorJpaDao;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;

import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaDao authorJpaDao;

    public AuthorRepositoryImpl(AuthorJpaDao authorJpaDao) {
        this.authorJpaDao = authorJpaDao;
    }

    @Override
    public List<AuthorEntity> getAll() {
        return authorJpaDao.getAll().stream()
                .map(AuthorEntity.INSTANCE::fromAuthorJpaEntityToAuthorEntity)
                .toList();
    }

    @Override
    public Optional<AuthorEntity> findBySlug(String slug) {
        return authorJpaDao.findBySlug(slug).map(AuthorEntity.INSTANCE::fromAuthorJpaEntityToAuthorEntity);
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        AuthorJpaEntity authorJpaEntity = AuthorEntity.INSTANCE.fromAuthorEntityToAuthorJpaEntity(authorEntity);
        if(authorEntity.id() == null) {
            return AuthorMapper.INSTANCE.fromAuthorJpaEntityToAuthorEntity(authorJpaDao.insert(authorJpaEntity));
        }

        return AuthorMapper.ISTANCE.fromAuthorJpaEntityToAuthorEntity(authorJpaDao.update(authorJpaEntity));
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorJpaDao.findById(id).map(AuthorEntity.INSTANCE::fromAuthorJpaEntityToAuthorEntity);
    }

    @Override
    public void deleteBySlug(String slug) {
        authorJpaDao.deleteBySlug(slug);
    }
}
