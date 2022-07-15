package com.pf.projectboard.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        LocalDateTime createdAt,
        String createdBy,
        String content
) {

    public static ArticleCommentDto of(LocalDateTime createdAt, String createdBy, String content) {
        return new ArticleCommentDto(createdAt, createdBy, content);
    }
}
