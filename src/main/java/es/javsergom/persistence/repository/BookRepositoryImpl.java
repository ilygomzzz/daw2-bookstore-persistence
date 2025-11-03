package es.javsergom.persistence.repository;

import es.javierserrano.domain.model.Page;
import es.javierserrano.domain.repository.BookRepository;
import es.javierserrano.domain.repository.entity.BookEntity;
import es.javsergom.persistence.dao.jpa.BookJpaDao;
import es.javsergom.persistence.dao.jpa.entity.BookJpaEntity;
import es.javsergom.persistence.repository.mapper.BookMapper;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final BookJpaDao bookJpaDao;

    public BookRepositoryImpl(BookJpaDao bookJpaDao) {
        this.bookJpaDao = bookJpaDao;
    }

    @Override
    public Page<BookEntity> findAll(int page, int size) {
        List<BookEntity> bookEntities = bookJpaDao.findAll(page, size).stream()
                .map(BookMapper::fromBookJpaEntityToBookEntity)
                .toList();

        long totalElements =  bookJpaDao.count();
        return new Page<>(bookEntities, page, size, totalElements);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return bookJpaDao.findByIsbn(isbn).map(BookMapper::fromBookJpaEntityToBookEntity);
    }

    @Override
    public BookEntity save(BookEntity bookEntity) {
        BookJpaEntity bookJpaEntity = BookMapper.fromBookEntityToBookJpaEntity(bookEntity);
        if (bookEntity.id() == null) {
            return BookMapper.fromBookJpaEntityToBookEntity(bookJpaDao.insert(bookJpaEntity));
        }

        return BookMapper.fromBookJpaEntityToBookEntity(bookJpaDao.update(bookJpaEntity));
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        return bookJpaDao.findById(id).map(BookMapper::fromBookJpaEntityToBookEntity);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookJpaDao.deleteByIsbn(isbn);
    }
}
