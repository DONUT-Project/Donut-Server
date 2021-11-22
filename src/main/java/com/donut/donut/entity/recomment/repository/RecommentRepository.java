package com.donut.donut.entity.recomment.repository;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.recomment.Recomment;
import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Optional<Recomment> findByRecommentId(Long recommentId);
    void deleteByRecommentId(Long recommentId);
    List<Recomment> findAllByCommentId(Comment commentId);
    void deleteAllByCommentId(Comment commentId);
    void deleteAllByUser(User user);
}
