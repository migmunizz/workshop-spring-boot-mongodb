package com.miguelmuniz.dto;

import com.miguelmuniz.domain.User;

public record AuthorDTO(
        String id,
        String name
) {
        public AuthorDTO(User obj) {
            this(
                    obj.getId(),
                    obj.getName()
            );
        }
}
