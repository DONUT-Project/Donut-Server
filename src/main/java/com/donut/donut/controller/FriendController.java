package com.donut.donut.controller;

import com.donut.donut.payload.response.UserResponse;
import com.donut.donut.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    public List<UserResponse> getMyFriends(@RequestHeader("Authorization") String token) {
        return friendService.getMyFriends(token);
    }

    @GetMapping("/info/{friendId}")
    public UserResponse getMyFriend(@RequestHeader("Authorization") String token,
                                    @PathVariable Long friendId) {
        return friendService.getFriendInfo(token, friendId);
    }

    @PostMapping("/{friendId}")
    public void makeFriend(@RequestHeader("Authorization") String token,
                           @PathVariable Long friendId) {
        friendService.makeFriend(token, friendId);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@RequestHeader("Authorization") String token,
                             @PathVariable Long friendId) {
        friendService.deleteFriend(token, friendId);
    }
}
