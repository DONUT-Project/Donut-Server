package com.donut.donut.service.friend;

import com.donut.donut.payload.response.UserResponse;

import java.util.List;

public interface FriendService {
    void makeFriend(String token, Long friendId);
    void deleteFriend(String token, Long friendId);
    UserResponse getFriendInfo(String token, Long friendId);
    List<UserResponse> getMyFriends(String token);
}
