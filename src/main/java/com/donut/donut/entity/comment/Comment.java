package com.donut.donut.entity.comment;

import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.recomment.Recomment;
import com.donut.donut.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "kakao_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doneId")
    private Done done;

    private String comment;

    private LocalDateTime writeAt;

    private Boolean isPublic;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Recomment> recomments;
}
