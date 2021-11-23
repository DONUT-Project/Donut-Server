package com.donut.donut.controller;

import com.donut.donut.payload.request.SignUpRequest;
import com.donut.donut.payload.response.UserResponse;
import com.donut.donut.service.user.UserService;
import com.donut.donut.util.FcmUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final FcmUtil fcmUtil;
    private final UserService userService;

    @GetMapping
    public UserResponse getMyInfo(@RequestHeader("Authorization") String token) {
        return userService.getMyInfo(token);
    }

    @GetMapping("/{kakaoId}")
    public UserResponse getFriendInfo(@RequestHeader("Authorization") String token,
                                      @PathVariable Long kakaoId) {
        return userService.getFriends(token, kakaoId);
    }

    @PostMapping
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
    }

    @PutMapping("/comment")
    public void updateIsComment(@RequestHeader("Authorization") String token,
                                @RequestParam Boolean isComment) {
        userService.updateIsNotificationComment(token, isComment);
    }

    @PutMapping("/friend")
    public void updateIsFriend(@RequestHeader("Authorization") String token,
                               @RequestParam Boolean isFriend) {
        userService.updateIsNotificationFriend(token, isFriend);
    }

    @DeleteMapping
    public void deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
    }

    @PostMapping("/test/{deviceToken}")
    public void testPushNotification(@PathVariable String deviceToken) {
        List<String> deviceTokens = new ArrayList<>();
        deviceTokens.add(deviceToken);

        fcmUtil.sendPushMessage(deviceTokens, "test", "test");
    }
}
