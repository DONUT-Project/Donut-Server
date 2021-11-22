package com.donut.donut.service.user;

import com.donut.donut.entity.comment.repository.CommentRepository;
import com.donut.donut.entity.device_token.repository.DeviceTokenRepository;
import com.donut.donut.entity.done.repository.DoneRepository;
import com.donut.donut.entity.friend.repository.FriendRepository;
import com.donut.donut.entity.recomment.repository.RecommentRepository;
import com.donut.donut.entity.refresh_token.repository.RefreshTokenRepository;
import com.donut.donut.entity.user.User;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.AlreadySignedException;
import com.donut.donut.error.exceptions.UserNotFoundException;
import com.donut.donut.payload.request.SignUpRequest;
import com.donut.donut.payload.response.UserResponse;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final DoneRepository doneRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final CommentRepository commentRepository;
    private final FriendRepository friendRepository;
    private final RecommentRepository recommentRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByKakaoId(signUpRequest.getKakaoId()))
            throw new AlreadySignedException();

        userRepository.save(
                User.builder()
                        .kakaoId(signUpRequest.getKakaoId())
                        .userImageUrl(signUpRequest.getProfileUrl())
                        .nickName(signUpRequest.getNickName())
                        .build()
        );
    }

    @Override
    public UserResponse getMyInfo(String token) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        return UserResponse.builder()
                .userId(user.getKakaoId())
                .name(user.getNickName())
                .profileUrl(user.getUserImageUrl())
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(String token) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);


        recommentRepository.deleteAllByUser(user);
        commentRepository.deleteAllByUser(user);
        doneRepository.deleteAllByUser(user);
        deviceTokenRepository.deleteAllByUser(user);
        refreshTokenRepository.deleteAllByKakaoId(user.getKakaoId());
        friendRepository.deleteAllByFriend(user);
        friendRepository.findAllByMe(user);
        userRepository.deleteByKakaoId(user.getKakaoId());
    }
}
