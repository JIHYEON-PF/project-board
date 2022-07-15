package com.pf.projectboard.service;

import com.pf.projectboard.domain.Article;
import com.pf.projectboard.domain.ArticleComment;
import com.pf.projectboard.dto.ArticleCommentDto;
import com.pf.projectboard.dto.ArticleCommentUpdateDto;
import com.pf.projectboard.repository.ArticleCommentRepository;
import com.pf.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments() {
        // Given
        Long articleId = 1L;
        given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#hashtag")));

        // When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        // Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);

    }

    @DisplayName("게시글 ID와 댓글 정보를 입력하면, 댓글을 저장")
    @Test
    void givenArticleIdAndArticleCommentInfo_whenSavingComments_thenSavesComments() {
        // Given
        Long articleId = 1L;
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveArticleComment(1L, ArticleCommentDto.of(LocalDateTime.now(), "Fkaa", "content"));

        // Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));

    }

    @DisplayName("댓글 ID와 댓글 정보를 입력하면, 댓글을 수정")
    @Test
    void givenArticleIdAndArticleCommentInfo_whenUpdatingComments_thenUpdatesComments() {
        // Given
        Long articleCommentId = 1L;
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.updateArticleComment(1L, ArticleCommentUpdateDto.of("Fkaa", "content"));

        // Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));

    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제")
    @Test
    void givenArticleId_whenDeletingComments_thenDeletesComments() {
        // Given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        // When
        sut.deleteArticleComment(articleCommentId);

        // Then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));

    }

}