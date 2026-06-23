package com.miguelmuniz.dto;

import java.time.Instant;

public record CommentDTO(
        String text,
        Instant date,
        AuthorDTO author
) {

}
