package com.donut.donut.entity.recomment;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recomment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recommentId;

    @ManyToOne
    @JoinColumn(name = "kakao_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment commentId;

    private String comment;

    private LocalDateTime writeAt;

    private Boolean isPublic;
}
