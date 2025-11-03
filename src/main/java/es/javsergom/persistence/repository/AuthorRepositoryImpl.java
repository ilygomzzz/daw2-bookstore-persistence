package es.javsergom.persistence.repository;

import es.javierserrano.domain.model.Page;
import es.javierserrano.domain.repository.AuthorRepository;
import es.javierserrano.domain.repository.entity.AuthorEntity;
import es.javsergom.persistence.dao.jpa.AuthorJpaDao;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.javsergom.persistence.repository.mapper.AuthorMapper;

import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaDao authorJpaDao;

    public AuthorRepositoryImpl(AuthorJpaDao authorJpaDao) {
        this.authorJpaDao = authorJpaDao;
    }

    @Override
    public Page<AuthorEntity> findAll(int page, int size) {
        List<AuthorEntity> authorEntities = authorJpaDao.findAll(page, size).stream()
                .map(AuthorMapper.INSTANCE::fromAuthorJpaEntityToAuthorEntity)
                .toList();

        long totalElements = authorJpaDao.count();

        return new Page<>(authorEntities, page, size, totalElements);
    }

    @Override
    public Optional<AuthorEntity> findBySlug(String slug) {
        return authorJpaDao.findBySlug(slug).map(AuthorMapper.INSTANCE::fromAuthorJpaEntityToAuthorEntity);
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        AuthorJpaEntity authorJpaEntity = AuthorMapper.INSTANCE.fromAuthorEntityToAuthorJpaEntity(authorEntity);
        if(authorEntity.id() == null) {
            return AuthorMapper.INSTANCE.fromAuthorJpaEntityToAuthorEntity(authorJpaDao.insert(authorJpaEntity));
        }

        return AuthorMapper.INSTANCE.fromAuthorJpaEntityToAuthorEntity(authorJpaDao.update(authorJpaEntity));
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorJpaDao.findById(id).map(AuthorMapper.INSTANCE::fromAuthorJpaEntityToAuthorEntity);
    }

    @Override
    public void deleteBySlug(String slug) {
        authorJpaDao.deleteBySlug(slug);
    }
}
