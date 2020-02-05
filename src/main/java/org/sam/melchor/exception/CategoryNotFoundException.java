package org.sam.melchor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {
        super(category + "'s post not found");
    }

    public CategoryNotFoundException(Long categoryId) {
        super(categoryId + "'s post not found");
    }
}
