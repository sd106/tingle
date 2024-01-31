package com.example.tingle.snapshot.entity;

import com.example.tingle.snapshot.dto.request.CommentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String context;

    /**
     * 유저 엔티티를 넣으려다가 스타의 코멘트와 팬의 코멘트를 분리할 필요가 있어서 그냥 엔티티에는 username만 받고,
     * 서비스나 컨트롤러에서 username을 통해 엔티티에서 찾는게 좋을 것 같아서 String으로 함.
     * 다만!!! 그러기 위해서는 유저 네임은 스타와 팬을 모두 합쳐서 하나만 존재해야 하므로,
     * 회원가입 부분에서 두 엔티티에서 모두 중복확인하도록 코드 수정이 필요하다.
     */
    private String username;

    @ManyToOne
    @JoinColumn(name = "snapshot_id")
    private SnapShotEntity snapShotEntity;

    public void update(String context) {
        this.context = context;
    }

}
