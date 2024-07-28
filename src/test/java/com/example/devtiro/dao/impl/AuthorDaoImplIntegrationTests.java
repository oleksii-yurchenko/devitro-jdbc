package com.example.devtiro.dao.impl;

import com.example.devtiro.TestDataUtils;
import com.example.devtiro.domain.Author;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author = TestDataUtils.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertTrue(result.isPresent(), "The result should be present");
        assertEquals(author, result.get(), "The retrieved author should match the created author");
    }

    @Test
    public void testThatFindReturnsAllAuthors(){
        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);

        List<Author> result = underTest.find();
        assertEquals(result.size(), 3);
        assertEquals(new HashSet<>(result), Set.of(authorA, authorB, authorC));
    }

    @Test
    public void testThatUpdateAuthorById(){
        Author authorA = TestDataUtils.createTestAuthorA();
        Author authorB = TestDataUtils.createTestAuthorB();
        Author authorC = TestDataUtils.createTestAuthorC();

        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);

        authorA.setId(4L);
        authorA.setAge(12);
        authorA.setName("Updated Name");

        underTest.update(2L, authorA);
        Optional<Author> newAuthor = underTest.findOne(4L);
        Optional<Author> oldAuthor = underTest.findOne(2L);
        assertTrue(newAuthor.isPresent());
        assertEquals(newAuthor.get(), authorA);
        assertFalse(oldAuthor.isPresent());
    }

    @Test
    public void shouldDeleteAuthorById(){
        Author author = TestDataUtils.createTestAuthorA();
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertFalse(result.isPresent());
    }

}
