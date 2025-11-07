package es.javsergom.persistence.dao.jpa.impl;

import es.javsergom.persistence.TestConfig;
import es.javsergom.persistence.dao.jpa.AuthorJpaDao;
import es.javsergom.persistence.dao.jpa.BookJpaDao;
import es.javsergom.persistence.dao.jpa.PublisherJpaDao;
import es.javsergom.persistence.dao.jpa.entity.AuthorJpaEntity;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJpaDaoImplTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookJpaDao bookJpaDao;

    @Autowired
    private AuthorJpaDao authorJpaDao;

    @Autowired
    private PublisherJpaDao publisherJpaDao;

    @Nested
    class findByIsbnTests {
        @Test
        @DisplayName("Given exist ISBN should return a book whit it")
        void findByIsbn() {
            Optional<BookJpaEntity> bookJpaEntity = bookJpaDao.findByIsbn("9780142424179");
            assertTrue(bookJpaEntity.isPresent());
        }
    }
}