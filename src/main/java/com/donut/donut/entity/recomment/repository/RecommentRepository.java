package com.donut.donut.entity.recomment.repository;

import com.donut.donut.entity.recomment.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Optional<Recomment> findByRecommentId(Long recommentId);
    void deleteByRecommentId(Long recommentId);
}
