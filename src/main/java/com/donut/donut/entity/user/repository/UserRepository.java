package com.donut.donut.entity.user.repository;

import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByKakaoId(Long kakaoId);
    Optional<User> findByKakaoId(Long kakaoId);
    void deleteByKakaoId(Long kakaoId);
}
