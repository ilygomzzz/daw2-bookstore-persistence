package es.javsergom.persistence;

import es.javsergom.persistence.dao.jpa.AuthorJpaDao;
import es.javsergom.persistence.dao.jpa.BookJpaDao;
import es.javsergom.persistence.dao.jpa.PublisherJpaDao;
import es.javsergom.persistence.dao.jpa.impl.AuthorJpaDaoImpl;
import es.javsergom.persistence.dao.jpa.impl.BookJpaDaoImpl;
import es.javsergom.persistence.dao.jpa.impl.PublisherJpaDaoImpl;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.javsergom.persistence.dao.jpa")
@EntityScan(basePackages = "es.javsergom-persistence.dao.jpa.entity")
public class TestConfig {
    @Bean
    public PublisherJpaDao publisherJpaDao(EntityManager entityManager) {
        return new PublisherJpaDaoImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao(EntityManager entityManager) {
        return new BookJpaDaoImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao(EntityManager entityManager) {
        return new AuthorJpaDaoImpl();
    }
}
