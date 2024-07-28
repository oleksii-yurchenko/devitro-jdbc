package com.example.devtiro.dao.impl;

import com.example.devtiro.TestDataUtils;
import com.example.devtiro.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void shouldGenerateCorrectSQLWhenCreatingBook(){

        Book book = TestDataUtils.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("TEST-123-123"), eq("Broken Hope"), eq(1L)
        );
    }

    @Test
    public void shouldGenerateCorrectSqlWhenFindingOne(){
        underTest.findOne("TEST-123-123");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ?"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("TEST-123-123")
        );
    }

    @Test
    public void shouldGenerateCorrectSQLWhenFindingAllBooks(){
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void shouldGenerateCorrectSQLWhenUpdating(){
        Book book = TestDataUtils.createTestBookA();

        underTest.update("TEST-121212", book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq(book.getIsbn()), eq(book.getTitle()), eq(book.getAuthorId()), eq("TEST-121212"));
    }

    @Test
    public void shouldGenerateCorrectSQLWhenDeleting(){
        underTest.delete("TEST");

        verify(jdbcTemplate).update(
                eq("DELETE FROM books WHERE isbn = ?"),
                eq("TEST")
        );
    }


}