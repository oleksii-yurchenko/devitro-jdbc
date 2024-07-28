package com.example.devtiro.dao.impl;

import com.example.devtiro.TestDataUtils;
import com.example.devtiro.dao.AuthorDao;
import com.example.devtiro.domain.Author;
import com.example.devtiro.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;

    private AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void shouldReturnOneBookWhileFindingOne(){
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtils.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertTrue(result.isPresent());
        assertEquals(result.get(), book);
    }

    @Test
    public void shouldReturnAllBooks(){
        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        authorDao.create(authorA);
        authorDao.create(authorB);

        Book bookA = TestDataUtils.createTestBookA();
        Book bookB = TestDataUtils.createTestBookB();
        Book bookC = TestDataUtils.createTestBookC();
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        List<Book> result = underTest.find();
        assertEquals(result.size(), 3);
        assertEquals(Set.of(bookA, bookB, bookC), new HashSet<>(result));
    }

    @Test
    public void shouldUpdateById(){
        Author authorA = TestDataUtils.createTestAuthorA();
        Book bookA = TestDataUtils.createTestBookA();
        authorDao.create(authorA);
        underTest.create(bookA);

        String oldIsbn = bookA.getIsbn();
        bookA.setIsbn("111");
        bookA.setTitle("Test Title");

        underTest.update(oldIsbn, bookA);
        Optional<Book> newBook = underTest.findOne(bookA.getIsbn());
        Optional<Book> oldBook = underTest.findOne(oldIsbn);
        assertTrue(newBook.isPresent());
        assertFalse(oldBook.isPresent());
        assertEquals(newBook.get(), bookA);
    }

    @Test
    public void shouldDeleteById(){
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtils.createTestBookA();
        underTest.create(book);
        underTest.delete(book.getIsbn());
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertFalse(result.isPresent());
    }
}
