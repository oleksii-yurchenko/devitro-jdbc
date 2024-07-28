package com.example.devtiro;

import com.example.devtiro.domain.Author;
import com.example.devtiro.domain.Book;

public class TestDataUtils {
    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("John Smith")
                .age(70)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Anna Adams")
                .age(60)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("TEST-123-123")
                .title("Broken Hope")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("TEST-111-111")
                .title("Ancient Sun")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("TEST-666-555")
                .title("Deep advice")
                .authorId(2L)
                .build();
    }
}
