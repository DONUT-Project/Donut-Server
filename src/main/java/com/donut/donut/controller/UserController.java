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

    @PostMapping
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
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
