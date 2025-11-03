package es.javsergom.util;

import es.javierserrano.domain.repository.entity.AuthorEntity;
import es.javierserrano.domain.repository.entity.BookEntity;
import es.javierserrano.domain.repository.entity.PublisherEntity;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;
import es.javsergom.persistence.dao.jpa.entity.PublisherJpaEntity;
import org.instancio.Instancio;
import org.instancio.Model;

import java.math.BigDecimal;
import java.util.List;

import static org.instancio.Select.field;

public class InstancioModel {

    private static final String ISBN_PATTERN = "#d#d#d#d#d#d#d#d#d#d#d#d#d";
    private static final String SLUG_PATTERN = "#c#c#c-#c#c#c";

    // Publisher

    public static final Model<PublisherJpaEntity> PUBLISHER_JPA_ENTITY_MODEL = Instancio.of(PublisherJpaEntity.class)
            .generate(field(PublisherJpaEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();

    public static final Model<PublisherEntity> PUBLISHER_ENTITY_MODEL = Instancio.of(PublisherEntity.class)
            .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();

    // Author

    public static final Model<AuthorJpaEntity> AUTHOR_JPA_ENTITY_MODEL = Instancio.of(AuthorJpaEntity.class)
            .generate(field(AuthorJpaEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();

    public static final Model<AuthorEntity> AUTHOR_ENTITY_MODEL = Instancio.of(AuthorEntity.class)
            .generate(field(PublisherEntity.class, "slug"), gen -> gen.text().pattern(SLUG_PATTERN))
            .toModel();

    public static final Model<List<AuthorJpaEntity>> AUTHOR_JPA_ENTITY_LIST_MODEL = Instancio.ofList(AUTHOR_JPA_ENTITY_MODEL).toModel();
    public static final Model<List<AuthorEntity>> AUTHOR_ENTITY_LIST_MODEL = Instancio.ofList(AUTHOR_ENTITY_MODEL).toModel();

    // Book

    public static final Model<BookJpaEntity> BOOK_JPA_ENTITY_MODEL = Instancio.of(BookJpaEntity.class)
            .generate(field(BookJpaEntity::getIsbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookJpaEntity::getDiscountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookJpaEntity::getPublicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(BookJpaEntity::getPublisher), PUBLISHER_JPA_ENTITY_MODEL)
            .setModel(field(BookJpaEntity::getBookAuthors), AUTHOR_JPA_ENTITY_LIST_MODEL)
            .toModel();

    public static final Model<BookEntity> BOOK_ENTITY_MODEL = Instancio.of(BookEntity.class)
            .generate(field(BookEntity::isbn), gen -> gen.text().pattern(ISBN_PATTERN))
            .generate(field(BookEntity::discountPercentage), gen -> gen.math().bigDecimal().range(new BigDecimal("0.00"), new BigDecimal("100.00")))
            .generate(field(BookEntity::publicationDate), gen -> gen.temporal().localDate().past())
            .setModel(field(BookEntity::publisher), PUBLISHER_ENTITY_MODEL)
            .setModel(field(BookEntity::authors), AUTHOR_ENTITY_LIST_MODEL)
            .toModel();
}
