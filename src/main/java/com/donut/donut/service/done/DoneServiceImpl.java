package com.donut.donut.service.done;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.comment.repository.CommentRepository;
import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.done.repository.DoneRepository;
import com.donut.donut.entity.friend.Friend;
import com.donut.donut.entity.friend.repository.FriendRepository;
import com.donut.donut.entity.recomment.repository.RecommentRepository;
import com.donut.donut.entity.user.User;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.DoneNotFoundException;
import com.donut.donut.error.exceptions.FriendNotFoundException;
import com.donut.donut.error.exceptions.UserNotFoundException;
import com.donut.donut.payload.request.UpdateDoneRequest;
import com.donut.donut.payload.request.WriteDoneRequest;
import com.donut.donut.payload.response.DoneResponse;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class DoneServiceImpl implements DoneService {

    private final UserRepository userRepository;
    private final DoneRepository doneRepository;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    private final JwtProvider jwtProvider;

    private <T>void setIfNotNull(Consumer<T> setter, T value) {
        if(!value.equals(""))
            setter.accept(value);
    }

    private List<DoneResponse> setResponse(List<Done> dones) {
        List<DoneResponse> doneResponses = new ArrayList<>();

        dones.forEach(done -> {
            doneResponses.add(
                    DoneResponse.builder()
                            .doneId(done.getDoneId())
                            .kakaoId(done.getUser().getKakaoId())
                            .userName(done.getUser().getNickName())
                            .title(done.getTitle())
                            .content(done.getContent())
                            .writeAt(done.getWriteAt())
                            .isPublic(done.getIsPublic())
                            .build()
            );
        });

        return doneResponses;
    }

    @Override
    public List<DoneResponse> readDone(String token) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        List<Done> dones = doneRepository.findAllByUser(user);

        return setResponse(dones);
    }

    @Override
    public List<DoneResponse> searchDoneByWriteAt(String token, LocalDate writeAt) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        List<Done> dones = doneRepository.findAllByUserAndWriteAt(user, writeAt);

        return setResponse(dones);
    }

    @Override
    public List<DoneResponse> readFriendDone(String token, Long friendId, LocalDate date) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Friend friend = friendRepository.findByFriend_KakaoIdAndMe(friendId, user)
                .orElseThrow(FriendNotFoundException::new);

        List<Done> dones = doneRepository.findAllByUserAndWriteAt(friend.getFriend(), date);

        return setResponse(dones);
    }

    @Override
    @Transactional
    public void writeDone(String token, WriteDoneRequest writeDoneRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        doneRepository.save(
                Done.builder()
                        .content(writeDoneRequest.getContent().equals("") ? "" : writeDoneRequest.getContent() )
                        .user(user)
                        .title(writeDoneRequest.getTitle().equals("") ? "" : writeDoneRequest.getTitle())
                        .isPublic(writeDoneRequest.getIsPublic())
                        .writeAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                        .build()
        );
    }

    @Override
    public void updateDone(String token, Long doneId, UpdateDoneRequest updateDoneRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Done done = doneRepository.findByDoneIdAndUser(doneId, user)
                .orElseThrow(DoneNotFoundException::new);

        setIfNotNull(done::setTitle, updateDoneRequest.getTitle());
        setIfNotNull(done::setContent, updateDoneRequest.getContent());

        doneRepository.save(done);
    }

    @Override
    public void updatePublic(String token,Long doneId, Boolean isPublic) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Done done = doneRepository.findByDoneIdAndUser(doneId, user)
                .orElseThrow(DoneNotFoundException::new);

        done.setIsPublic(isPublic);

        doneRepository.save(done);
    }

    @Override
    @Transactional
    public void deleteDone(String token, Long doneId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Done done = doneRepository.findByDoneIdAndUser(doneId, user)
                .orElseThrow(DoneNotFoundException::new);

        List<Comment> comments = commentRepository.findByDone(done);

        for (Comment comment : comments) {
            recommentRepository.deleteAllByCommentId(comment);
        }

        commentRepository.deleteAllByDone(done);
        doneRepository.deleteByDoneId(done.getDoneId());
    }
}
