package com.donut.donut.entity.done;

import com.donut.donut.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Done {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doneId;

    @ManyToOne
    @JoinColumn(name = "kakao_id")
    private User user;

    private String title;

    private String content;

    private Boolean isPublic;

    private LocalDate writeAt;
}
