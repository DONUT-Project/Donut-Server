package com.donut.donut.service.friend;

import com.donut.donut.entity.device_token.DeviceToken;
import com.donut.donut.entity.friend.Friend;
import com.donut.donut.entity.friend.repository.FriendRepository;
import com.donut.donut.entity.user.User;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.AlreadyFriendException;
import com.donut.donut.error.exceptions.FriendNotFoundException;
import com.donut.donut.error.exceptions.UserNotFoundException;
import com.donut.donut.payload.response.UserResponse;
import com.donut.donut.util.FcmUtil;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    private final JwtProvider jwtProvider;
    private final FcmUtil fcmUtil;

    @Override
    @Transactional
    public void makeFriend(String token, Long friendId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        User friend = userRepository.findByKakaoId(friendId)
                .orElseThrow(UserNotFoundException::new);

        Friend frd = friendRepository.findByFriend_KakaoIdAndMe(friendId, user)
                .orElse(null);

        if(frd != null)
            throw new AlreadyFriendException();

        friendRepository.save(
                Friend.builder()
                        .me(user)
                        .friend(friend)
                        .build()
        );

        friendRepository.save(
                Friend.builder()
                        .friend(user)
                        .me(friend)
                        .build()
        );

        if(friend.getIsFriendNotification()) {
            List<String> tokens = new ArrayList<>();
            for (DeviceToken deviceToken : friend.getDeviceTokens()) {
                tokens.add(deviceToken.getDeviceToken());
            }

            fcmUtil.sendPushMessage(tokens, "????????? ???????????????", user.getNickName() + "?????? ????????? ???????????????!");
        }
    }

    @Override
    @Transactional
    public void deleteFriend(String token, Long friendId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        User friend = userRepository.findByKakaoId(friendId)
                .orElseThrow(UserNotFoundException::new);

        friendRepository.findByFriend_KakaoIdAndMe(friendId, user)
                .orElseThrow(FriendNotFoundException::new);

        friendRepository.deleteAllByMeAndFriendOrFriendAndMe(user, friend, user, friend);
    }

    @Override
    public UserResponse getFriendInfo(String token, Long friendId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        User friend = userRepository.findByKakaoId(friendId)
                .orElseThrow(UserNotFoundException::new);

        friendRepository.findByFriend_KakaoIdAndMe(friendId, user)
                .orElseThrow(FriendNotFoundException::new);

        return UserResponse.builder()
                .userId(friend.getKakaoId())
                .name(friend.getNickName())
                .profileUrl(friend.getUserImageUrl())
                .build();
    }

    @Override
    public List<UserResponse> getMyFriends(String token) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        List<Friend> myFriends = friendRepository.findAllByMe(user);
        List<UserResponse> responses = new ArrayList<>();

        for(Friend friend : myFriends) {
            responses.add(
                    UserResponse.builder()
                            .profileUrl(friend.getFriend().getUserImageUrl())
                            .name(friend.getFriend().getNickName())
                            .userId(friend.getFriend().getKakaoId())
                            .isNotificationFriend(friend.getFriend().getIsFriendNotification())
                            .isNotificationComment(friend.getFriend().getIsCommentNotification())
                            .build()
            );
        }

        return responses;
    }
}
