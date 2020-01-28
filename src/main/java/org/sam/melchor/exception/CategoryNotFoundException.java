package org.sam.melchor.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {
        super(category + "'s post not found");
    }
}
