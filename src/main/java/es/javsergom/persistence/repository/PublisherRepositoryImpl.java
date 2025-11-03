package es.javsergom.persistence.repository;

import es.javierserrano.domain.model.Page;
import es.javierserrano.domain.repository.PublisherRepository;
import es.javierserrano.domain.repository.entity.PublisherEntity;
import es.javsergom.persistence.dao.jpa.PublisherJpaDao;
import es.javsergom.persistence.dao.jpa.entity.PublisherJpaEntity;
import es.javsergom.persistence.repository.mapper.PublisherMapper;

import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {

    private final PublisherJpaDao publisherJpaDao;

    public PublisherRepositoryImpl(PublisherJpaDao publisherJpaDao) {
        this.publisherJpaDao = publisherJpaDao;
    }

    @Override
    public Page<PublisherEntity> findAll(int page, int size) {
        List<PublisherEntity> publisherEntities = publisherJpaDao.findAll(page,size).stream()
                .map(PublisherMapper::fromPublisherJpaEntityToPublisherEntity)
                .toList();

        long totalElements = publisherJpaDao.count();
        return new Page<>(publisherEntities, page, size, totalElements);
    }

    @Override
    public Optional<PublisherEntity> findById(Long id) {
        return publisherJpaDao.findById(id).map(PublisherMapper::fromPublisherJpaEntityToPublisherEntity);
    }

    @Override
    public Optional<PublisherEntity> findBySlug(String slug) {
        return publisherJpaDao.findBySlug(slug).map(PublisherMapper::fromPublisherJpaEntityToPublisherEntity);
    }

    @Override
    public PublisherEntity save(PublisherEntity publisherEntity) {
        PublisherJpaEntity publisherJpaEntity = PublisherMapper.fromPublisherEntityToPublisherJpaEntity(publisherEntity);
        if(publisherEntity.id() == null) {
            return PublisherMapper.fromPublisherJpaEntityToPublisherEntity(publisherJpaDao.insert(publisherJpaEntity));
        }

        return PublisherMapper.fromPublisherJpaEntityToPublisherEntity(publisherJpaDao.update(publisherJpaEntity));
    }

    @Override
    public void deleteBySlug(String slug) {
        publisherJpaDao.deleteBySlug(slug);
    }
}
