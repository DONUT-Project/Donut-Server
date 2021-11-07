package com.donut.donut.entity.user;

import com.donut.donut.entity.comment.Comment;
import com.donut.donut.entity.done.Done;
import com.donut.donut.entity.recomment.Recomment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long kakaoId;

    private String nickName;

    private String userImageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Done> dones;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recomment> recomments;

    public void updateImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;

    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }
}
