package com.donut.donut.controller;

import com.donut.donut.payload.request.UpdateCommentRequest;
import com.donut.donut.payload.request.WriteCommentRequest;
import com.donut.donut.payload.response.CommentResponse;
import com.donut.donut.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{doneId}")
    public List<CommentResponse> getAllComment(@RequestHeader("Authorization") String token,
                                               @PathVariable Long doneId) {
        return commentService.readComment(token, doneId);
    }

    @PostMapping("/{doneId}")
    public void writeComment(@RequestHeader("Authorization") String token,
                          @RequestBody WriteCommentRequest writeCommentRequest,
                          @PathVariable Long doneId) {
        commentService.writeComment(token, doneId, writeCommentRequest);
    }

    @PostMapping("/recomment/{commentId}")
    public void writeReComment(@PathVariable Long commentId,
                               @RequestHeader("Authorization") String token,
                               @RequestBody WriteCommentRequest writeCommentRequest) {
        commentService.writeReComment(token, commentId, writeCommentRequest);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long commentId,
                              @RequestHeader("Authorization") String token,
                              @RequestBody UpdateCommentRequest updateCommentRequest) {
        commentService.updateComment(token, commentId, updateCommentRequest);
    }

    @PutMapping("/recomment/{recommentId}")
    public void updateReComment(@PathVariable Long recommentId,
                                @RequestHeader("Authorization") String token,
                                @RequestBody UpdateCommentRequest updateCommentRequest) {
        commentService.updateReComment(token, recommentId, updateCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @RequestHeader("Authorization") String token) {
        commentService.deleteComment(token, commentId);
    }

    @DeleteMapping("/recomment/{recommentId}")
    public void deleteReComment(@PathVariable Long recommentId,
                                @RequestHeader("Authorization") String token) {
        commentService.deleteReComment(token, recommentId);
    }

}
