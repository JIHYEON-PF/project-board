package com.pf.projectboard.dto;

public record ArticleCommentUpdateDto(String createdBy, String content) {

    public static ArticleCommentUpdateDto of(String createdBy, String content) {
        return new ArticleCommentUpdateDto(createdBy, content);
    }
}
