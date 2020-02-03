package org.sam.melchor.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {
        super(category + "'s post not found");
    }

    public CategoryNotFoundException(Long categoryId) {
        super(categoryId + "'s post not found");
    }
}
