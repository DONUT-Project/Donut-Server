package com.donut.donut.entity.comment.repository;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentId(Long commentId);
    List<Comment> findByDone(Done done);
    void deleteByCommentId(Long commentId);
    void deleteAllByDone(Done done);
    void deleteAllByUser(User user);
}
