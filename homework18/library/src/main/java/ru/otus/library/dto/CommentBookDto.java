package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentBookDto {
    private Long commentId;

    private String textComment;

    private String book;
}
