package es.javsergom.persistence.repository.mapper;

import es.javierserrano.domain.repository.entity.PublisherEntity;
import es.javsergom.persistence.dao.jpa.entity.PublisherJpaEntity;

public class PublisherMapper {
    public static PublisherEntity fromPublisherJpaEntityToPublisherEntity(PublisherJpaEntity publisher) {
        return new PublisherEntity(
                publisher.getId(),
                publisher.getName(),
                publisher.getSlug()
        );
    }

    public static PublisherJpaEntity fromPublisherEntityToPublisherJpaEntity(PublisherEntity publisher) {
        return new PublisherJpaEntity(
                publisher.id(),
                publisher.name(),
                publisher.slug()
        );
    }
}
