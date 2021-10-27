package com.donut.donut.service.comment;

import com.donut.donut.payload.request.UpdateCommentRequest;
import com.donut.donut.payload.request.WriteCommentRequest;
import com.donut.donut.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> readComment(String token, Long doneId);
    void writeComment(String token, Long doneId, WriteCommentRequest writeCommentRequest);
    void writeReComment(String token, Long commentId, WriteCommentRequest writeCommentRequest);
    void updateComment(String token, Long commentId, UpdateCommentRequest updateCommentRequest);
    void updateReComment(String token, Long commentId, UpdateCommentRequest updateCommentRequest);
    void deleteComment(String token, Long commentId);
    void deleteReComment(String token, Long reCommentId);
}
