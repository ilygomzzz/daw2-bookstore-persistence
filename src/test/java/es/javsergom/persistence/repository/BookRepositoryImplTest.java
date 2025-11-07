package es.javsergom.persistence.repository;

import es.javierserrano.domain.model.Page;
import es.javierserrano.domain.repository.entity.BookEntity;
import es.javsergom.persistence.dao.jpa.BookJpaDao;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;
import es.javsergom.persistence.repository.mapper.BookMapper;
import es.javsergom.util.InstancioModel;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {
    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(1,10,10L,Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(10).withSeed(20).create()),
                Arguments.of(1,6,10L,Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(6).withSeed(30).create()),
                Arguments.of(1,5,10L,Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(5).withSeed(40).create()),
                Arguments.of(1,4,10L,Instancio.ofList(InstancioModel.BOOK_JPA_ENTITY_MODEL).size(4).withSeed(50).create())
        );
    }

    @ParameterizedTest
    @DisplayName("Test findAll should returns list of BookEntity")
    @MethodSource("data")
    void testFindAll(int pageN, int size, long count, List<BookJpaEntity> bookJpaEntities) {
        when(bookJpaDao.findAll(anyInt(),anyInt())).thenReturn(bookJpaEntities);
        when(bookJpaDao.count()).thenReturn(count);

        Page<BookEntity> page = bookRepository.findAll(pageN, size);
        List<BookEntity> expectedBooks = bookJpaEntities.stream()
                .map(BookMapper::fromBookJpaEntityToBookEntity)
                .toList();
        assertAll(
                () -> assertEquals(expectedBooks, page.data()),
                () -> assertEquals(pageN, page.pageNumber()),
                () -> assertEquals(size, page.pageSize()),
                () -> assertEquals(count, page.totalElements())
        );
    }

    @Nested
    class FindByISBNTests {
        @Test
        @DisplayName("findByISBN should return BookEntity with it")
        void testFindByISBN() {
            BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).create();

            when(bookJpaDao.findByIsbn(anyString())).thenReturn(Optional.of(bookJpaEntity));

            Optional<BookEntity> expected = Optional.of(BookMapper.fromBookJpaEntityToBookEntity(bookJpaEntity));
            Optional<BookEntity> result = bookRepository.findByIsbn("some-isbn");

            assertAll(
                    () -> assertTrue(result.isPresent(), "BookEntity should be present"),
                    () -> assertEquals(expected.get().isbn(), result.get().isbn(), "ISBNs should match"),
                    () -> assertEquals(expected.get().titleEs(), result.get().titleEs(), "TitleEs should match"),
                    () -> assertEquals(expected.get().titleEn(), result.get().titleEn(), "TitleEn should match"),
                    () -> assertEquals(expected.get().synopsisEs(), result.get().synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(expected.get().synopsisEn(), result.get().synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(expected.get().basePrice(), result.get().basePrice(), "BasePrices should match"),
                    () -> assertEquals(expected.get().discountPercentage(), result.get().discountPercentage(), "DiscountPercentages should match"),
                    () -> assertEquals(expected.get().cover(), result.get().cover(), "Covers should match"),
                    () -> assertEquals(expected.get().publicationDate(), result.get().publicationDate(), "PublicationDates should match"),
                    () -> assertEquals(expected.get().publisher().id(), result.get().publisher().id(), "Publisher IDs should match"),
                    () -> assertEquals(expected.get().authors().size(), result.get().authors().size(), "Number of authors should match"),
                    () -> assertEquals(expected.get().authors().getFirst().id(), result.get().authors().getFirst().id(), "First author IDs should match"),
                    () -> assertEquals(expected.get().authors().getLast().id(), result.get().authors().getLast().id(), "Last author IDs should match")
            );
        }


        @Test
        @DisplayName("findByIsbn should return empty when not found")
        void findByIsbn_ShouldReturnEmptyWhenNotFound() {
            when(bookJpaDao.findByIsbn(anyString()))    .thenReturn(Optional.empty());

            Optional<BookEntity> result = bookRepository.findByIsbn("non-existent-isbn");

            // Assert
            assertFalse(result.isPresent(), "BookEntity should not be present");
        }
    }

    @Nested
    class findByIdTests {
        @Test
        @DisplayName("findById should return BookEntity with it")
        void testFindById() {
            BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).create();

            when(bookJpaDao.findById(anyLong())).thenReturn(Optional.of(bookJpaEntity));

            Optional<BookEntity> expected = Optional.of(BookMapper.fromBookJpaEntityToBookEntity(bookJpaEntity));
            Optional<BookEntity> result = bookRepository.findById(1L);

            assertAll(
                    () -> assertTrue(result.isPresent(), "BookEntity should be present"),
                    () -> assertEquals(expected.get().isbn(), result.get().isbn(), "ISBNs should match"),
                    () -> assertEquals(expected.get().titleEs(), result.get().titleEs(), "TitleEs should match"),
                    () -> assertEquals(expected.get().titleEn(), result.get().titleEn(), "TitleEn should match"),
                    () -> assertEquals(expected.get().synopsisEs(), result.get().synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(expected.get().synopsisEn(), result.get().synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(expected.get().basePrice(), result.get().basePrice(), "BasePrices should match"),
                    () -> assertEquals(expected.get().discountPercentage(), result.get().discountPercentage(), "DiscountPercentages should match"),
                    () -> assertEquals(expected.get().cover(), result.get().cover(), "Covers should match"),
                    () -> assertEquals(expected.get().publicationDate(), result.get().publicationDate(), "PublicationDates should match"),
                    () -> assertEquals(expected.get().publisher().id(), result.get().publisher().id(), "Publisher IDs should match"),
                    () -> assertEquals(expected.get().authors().size(), result.get().authors().size(), "Number of authors should match"),
                    () -> assertEquals(expected.get().authors().getFirst().id(), result.get().authors().getFirst().id(), "First author IDs should match"),
                    () -> assertEquals(expected.get().authors().getLast().id(), result.get().authors().getLast().id(), "Last author IDs should match")
            );
        }


        @Test
        @DisplayName("findById should return empty when not found")
        void findById_ShouldReturnEmptyWhenNotFound() {
            when(bookJpaDao.findById(anyLong())).thenReturn(Optional.empty());

            Optional<BookEntity> result = bookRepository.findById(9999L);

            // Assert
            assertFalse(result.isPresent(), "BookEntity should not be present");
        }
    }

    @Nested
    class SaveTests {
        @Test
        @DisplayName("Save without ID should insert")
        void saveWithoutId() {
            BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).ignore(field(BookJpaEntity::getId)).create();
            when(bookJpaDao.insert(bookJpaEntity)).thenReturn(bookJpaEntity);

            BookEntity expected = BookMapper.fromBookJpaEntityToBookEntity(bookJpaEntity);
            BookEntity result = bookRepository.save(expected);
            verify(bookJpaDao).insert(bookJpaEntity);

            assertAll(
                    () -> assertEquals(expected.isbn(), result.isbn(), "ISBNs should match"),
                    () -> assertEquals(expected.titleEs(), result.titleEs(), "TitleEs should match"),
                    () -> assertEquals(expected.titleEn(), result.titleEn(), "TitleEn should match"),
                    () -> assertEquals(expected.synopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(expected.synopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(expected.basePrice(), result.basePrice(), "BasePrices should match"),
                    () -> assertEquals(expected.discountPercentage(), result.discountPercentage(), "DiscountPercentages should match"),
                    () -> assertEquals(expected.cover(), result.cover(), "Covers should match"),
                    () -> assertEquals(expected.publicationDate(), result.publicationDate(), "PublicationDates should match"),
                    () -> assertEquals(expected.publisher().id(), result.publisher().id(), "Publisher IDs should match"),
                    () -> assertEquals(expected.authors().size(), result.authors().size(), "Number of authors should match"),
                    () -> assertEquals(expected.authors().getFirst().id(), result.authors().getFirst().id(), "First author IDs should match"),
                    () -> assertEquals(expected.authors().getLast().id(), result.authors().getLast().id(), "Last author IDs should match")
            );
        }

        @Test
        @DisplayName("Save with id should update")
        void saveWithId() {
            BookJpaEntity bookJpaEntity = Instancio.of(InstancioModel.BOOK_JPA_ENTITY_MODEL).create();
            when(bookJpaDao.update(bookJpaEntity)).thenReturn(bookJpaEntity);

            BookEntity expected = BookMapper.fromBookJpaEntityToBookEntity(bookJpaEntity);
            BookEntity result = bookRepository.save(expected);
            verify(bookJpaDao).update(bookJpaEntity);

            assertAll(
                    () -> assertEquals(expected.isbn(), result.isbn(), "ISBNs should match"),
                    () -> assertEquals(expected.titleEs(), result.titleEs(), "TitleEs should match"),
                    () -> assertEquals(expected.titleEn(), result.titleEn(), "TitleEn should match"),
                    () -> assertEquals(expected.synopsisEs(), result.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(expected.synopsisEn(), result.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(expected.basePrice(), result.basePrice(), "BasePrices should match"),
                    () -> assertEquals(expected.discountPercentage(), result.discountPercentage(), "DiscountPercentages should match"),
                    () -> assertEquals(expected.cover(), result.cover(), "Covers should match"),
                    () -> assertEquals(expected.publicationDate(), result.publicationDate(), "PublicationDates should match"),
                    () -> assertEquals(expected.publisher().id(), result.publisher().id(), "Publisher IDs should match"),
                    () -> assertEquals(expected.authors().size(), result.authors().size(), "Number of authors should match"),
                    () -> assertEquals(expected.authors().getFirst().id(), result.authors().getFirst().id(), "First author IDs should match"),
                    () -> assertEquals(expected.authors().getLast().id(), result.authors().getLast().id(), "Last author IDs should match")
            );
        }
    }

    @Test
    @DisplayName("Test DeleteByIsbn")
    void testDeleteByIsbn() {
        bookJpaDao.deleteByIsbn("12345");
        verify(bookJpaDao).deleteByIsbn("12345");
    }


}