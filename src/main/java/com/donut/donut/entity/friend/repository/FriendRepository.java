package com.donut.donut.entity.friend.repository;

import com.donut.donut.entity.friend.Friend;
import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFriend_KakaoIdAndMe(Long friend_kakaoId, User me);
    void deleteByFriendAndMe(User friend, User me);
    List<Friend> findAllByMe(User me);
}
