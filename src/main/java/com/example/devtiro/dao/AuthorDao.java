package com.example.devtiro.dao;

import com.example.devtiro.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void create(Author author);

    Optional<Author> findOne(long authorId);

    List<Author> find();

    void update(Long authorId, Author author);

    void delete(long id);
}
