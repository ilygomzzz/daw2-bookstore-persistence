package es.javsergom.persistence.repository.mapper;

import es.javierserrano.domain.repository.entity.BookEntity;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;

import java.time.LocalDate;
import java.util.List;

public class BookMapper {
    public static BookEntity fromBookJpaEntityToBookEntity(BookJpaEntity bookJpaEntity) {
         return new BookEntity(
                bookJpaEntity.getId(),
                bookJpaEntity.getIsbn(),
                bookJpaEntity.getTitleEs(),
                bookJpaEntity.getTitleEn(),
                bookJpaEntity.getSynopsisEs(),
                bookJpaEntity.getSynopsisEn(),
                bookJpaEntity.getBasePrice(),
                bookJpaEntity.getDiscountPercentage(),
                bookJpaEntity.getCover(),
                LocalDate.parse(bookJpaEntity.getPublicationDate()),
                PublisherMapper.fromPublisherJpaEntityToPublisherEntity(bookJpaEntity.getPublisher()),
                 (bookJpaEntity.getAuthors() != null && !bookJpaEntity.getAuthors().isEmpty())
                        ? bookJpaEntity.getAuthors().stream()
                            .map(AuthorMapper.INSTANCE::fromAuthorJpaEntityToAuthorEntity)
                            .toList()
                        : List.of()
        );
    }

    public static BookJpaEntity fromBookEntityToBookJpaEntity(BookEntity bookEntity) {
        BookJpaEntity bookJpaEntity =  new BookJpaEntity(
                bookEntity.id(),
                bookEntity.isbn(),
                bookEntity.titleEs(),
                bookEntity.titleEn(),
                bookEntity.synopsisEs(),
                bookEntity.synopsisEn(),
                bookEntity.basePrice(),
                bookEntity.discountPercentage(),
                bookEntity.cover(),
                bookEntity.publicationDate().toString(),
                PublisherMapper.fromPublisherEntityToPublisherJpaEntity(bookEntity.publisher())
        );

        bookJpaEntity.setAuthors(
                bookEntity.authors().stream()
                .map(AuthorMapper.INSTANCE::fromAuthorEntityToAuthorJpaEntity)
                .toList());

        return bookJpaEntity;
    }
}
