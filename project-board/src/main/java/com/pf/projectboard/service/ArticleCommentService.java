package com.pf.projectboard.service;

import com.pf.projectboard.dto.ArticleCommentDto;
import com.pf.projectboard.dto.ArticleCommentUpdateDto;
import com.pf.projectboard.repository.ArticleCommentRepository;
import com.pf.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(Long articleId, ArticleCommentDto dto) {
    }

    public void updateArticleComment(long ArticleCommentId, ArticleCommentUpdateDto dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
