package com.donut.donut.entity.friend;

import com.donut.donut.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "me")
    private User me;

    @ManyToOne
    @JoinColumn(name = "friend")
    private User friend;
}
