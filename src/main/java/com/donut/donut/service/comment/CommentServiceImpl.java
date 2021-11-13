package com.donut.donut.service.comment;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.comment.repository.CommentRepository;
import com.donut.donut.entity.device_token.DeviceToken;
import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.done.repository.DoneRepository;
import com.donut.donut.entity.friend.repository.FriendRepository;
import com.donut.donut.entity.recomment.Recomment;
import com.donut.donut.entity.recomment.repository.RecommentRepository;
import com.donut.donut.entity.user.User;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.*;
import com.donut.donut.payload.request.UpdateCommentRequest;
import com.donut.donut.payload.request.WriteCommentRequest;
import com.donut.donut.payload.response.CommentResponse;
import com.donut.donut.payload.response.ReCommentResponse;
import com.donut.donut.util.FcmUtil;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final DoneRepository doneRepository;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    private final JwtProvider jwtProvider;
    private final FcmUtil fcmUtil;

    private <T>void setIfNotNull(Consumer<T> setter, T value) {
        if(value != null)
            setter.accept(value);
    }

    @Override
    public List<CommentResponse> readComment(String token, Long doneId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Done done = doneRepository.findByDoneId(doneId)
                .orElseThrow(DoneNotFoundException::new);

        List<Comment> comments = commentRepository.findByDone(done);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(Comment comment : comments) {
            List<ReCommentResponse> responses = new ArrayList<>();
            for(Recomment recomment : comment.getRecomments()) {
                if(recomment.getIsPublic()) {
                    responses.add(
                            ReCommentResponse.builder()
                                    .comment(recomment.getComment())
                                    .commentId(recomment.getRecommentId())
                                    .reCommentId(recomment.getRecommentId())
                                    .isPublic(recomment.getIsPublic())
                                    .nickName(recomment.getUser().getNickName())
                                    .userId(recomment.getUser().getKakaoId())
                                    .writeAt(recomment.getWriteAt())
                                    .build()
                    );
                }else if(recomment.getIsPublic() && recomment.getUser().equals(user)) {
                    responses.add(
                            ReCommentResponse.builder()
                                    .comment(recomment.getComment())
                                    .commentId(recomment.getRecommentId())
                                    .reCommentId(recomment.getRecommentId())
                                    .isPublic(recomment.getIsPublic())
                                    .nickName(recomment.getUser().getNickName())
                                    .userId(recomment.getUser().getKakaoId())
                                    .writeAt(recomment.getWriteAt())
                                    .build()
                    );
                }else {
                    responses.add(
                            ReCommentResponse.builder()
                                    .comment("비공개 처리된 댓글입니다.")
                                    .commentId(recomment.getRecommentId())
                                    .reCommentId(recomment.getRecommentId())
                                    .isPublic(recomment.getIsPublic())
                                    .nickName(recomment.getUser().getNickName())
                                    .userId(recomment.getUser().getKakaoId())
                                    .writeAt(recomment.getWriteAt())
                                    .build()
                    );
                }

            }

            commentResponses.add(
                    CommentResponse.builder()
                            .doneId(done.getDoneId())
                            .commentId(comment.getCommentId())
                            .reComment(responses)
                            .comment(comment.getComment())
                            .isPublic(comment.getIsPublic())
                            .nickName(comment.getUser().getNickName())
                            .userId(comment.getUser().getKakaoId())
                            .writeAt(comment.getWriteAt())
                            .build()
            );
        }

        return commentResponses;
    }

    @Override
    public void writeComment(String token, Long doneId, WriteCommentRequest writeCommentRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Done done = doneRepository.findByDoneId(doneId)
                .orElseThrow(DoneNotFoundException::new);

        friendRepository.findByFriend_KakaoIdAndMe(done.getUser().getKakaoId(), user)
                .orElseThrow(FriendNotFoundException::new);

        Comment comment = commentRepository.save(
                Comment.builder()
                        .comment(writeCommentRequest.getComment())
                        .user(user)
                        .writeAt(LocalDateTime.now())
                        .done(done)
                        .isPublic(writeCommentRequest.getIsPublic())
                        .build()
        );

        User doner = userRepository.findByKakaoId(comment.getDone().getUser().getKakaoId())
                .orElseThrow(UserNotFoundException::new);

        List<String> tokens = new ArrayList<>();

        for(DeviceToken deviceToken : doner.getDeviceTokens()) {
            tokens.add(deviceToken.getDeviceToken());
        }

        fcmUtil.sendPushMessage(tokens, "댓글알림!", "Done리스트에 댓글이 왔습니다.");
    }

    @Override
    public void writeReComment(String token, Long commentId, WriteCommentRequest writeCommentRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);

        friendRepository.findByFriend_KakaoIdAndMe(comment.getDone().getUser().getKakaoId(), user)
                .orElseThrow(FriendNotFoundException::new);

        recommentRepository.save(
                Recomment.builder()
                        .commentId(comment)
                        .comment(writeCommentRequest.getComment())
                        .isPublic(writeCommentRequest.getIsPublic())
                        .writeAt(LocalDateTime.now())
                        .user(user)
                        .build()
        );

        User commenter = userRepository.findByKakaoId(comment.getUser().getKakaoId())
                .orElseThrow(UserNotFoundException::new);

        User doner = userRepository.findByKakaoId(comment.getDone().getUser().getKakaoId())
                .orElseThrow(UserNotFoundException::new);

        List<String> tokens = new ArrayList<>();
        for(DeviceToken deviceToken : commenter.getDeviceTokens()) {
            tokens.add(deviceToken.getDeviceToken());
        }

        for(DeviceToken deviceToken : doner.getDeviceTokens()) {
            tokens.add(deviceToken.getDeviceToken());
        }

        fcmUtil.sendPushMessage(tokens, "댓글알림!", "Done리스트에 댓글이 왔습니다.");
    }

    @Override
    public void updateComment(String token, Long commentId, UpdateCommentRequest updateCommentRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().equals(user))
            throw new NotMyCommentException();

        setIfNotNull(comment::setComment, updateCommentRequest.getComment());
        setIfNotNull(comment::setIsPublic, updateCommentRequest.getIsPublic());

        commentRepository.save(comment);
    }

    @Override
    public void updateReComment(String token, Long recommentId, UpdateCommentRequest updateCommentRequest) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Recomment recomment = recommentRepository.findByRecommentId(recommentId)
                .orElseThrow(ReCommentNotFoundException::new);

        if(!recomment.getUser().equals(user))
            throw new NotMyCommentException();

        setIfNotNull(recomment::setComment, updateCommentRequest.getComment());
        setIfNotNull(recomment::setIsPublic, updateCommentRequest.getIsPublic());

        recommentRepository.save(recomment);
    }

    @Override
    @Transactional
    public void deleteComment(String token, Long commentId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().equals(user) || !comment.getDone().getUser().equals(user))
            throw new NotMyCommentException();

        commentRepository.deleteByCommentId(commentId);
    }

    @Override
    public void deleteReComment(String token, Long reCommentId) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(RuntimeException::new);

        Recomment recomment = recommentRepository.findByRecommentId(reCommentId)
                .orElseThrow(RuntimeException::new);

        if(!recomment.getUser().equals(user) || recomment.getCommentId().getDone().getUser().equals(user))
            throw new RuntimeException("not have authority");

        recommentRepository.deleteByRecommentId(reCommentId);
    }
}
