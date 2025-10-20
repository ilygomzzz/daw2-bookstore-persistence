package es.javsergom.persistence.repository.mapper;

import es.javierserrano.domain.repository.entity.AuthorEntity;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;

public class AuthorMapper {

    public static AuthorMapper INSTANCE = null;

    static {
        INSTANCE = new AuthorMapper();
    }

    public AuthorJpaEntity fromAuthorEntityToAuthorJpaEntity(AuthorEntity authorEntity) {
        return new AuthorJpaEntity(
                authorEntity.id(),
                authorEntity.name(),
                authorEntity.nationality(),
                authorEntity.biographyEs(),
                authorEntity.biographyEn(),
                authorEntity.birthYear(),
                authorEntity.slug()
        );
    }

    public AuthorEntity fromAuthorJpaEntityToAuthorEntity(AuthorJpaEntity authorJpaEntity) {
        return new AuthorEntity(
                authorJpaEntity.getId(),
                authorJpaEntity.getName(),
                authorJpaEntity.getNationality(),
                authorJpaEntity.getBiographyEs(),
                authorJpaEntity.getBiographyEn(),
                authorJpaEntity.getBirthYear(),
                authorJpaEntity.getDeathYear(),
                authorJpaEntity.getSlug()
        );
    }
}
