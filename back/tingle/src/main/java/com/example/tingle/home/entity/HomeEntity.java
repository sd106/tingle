package com.example.tingle.home.entity;

import com.example.tingle.home.dto.HomeDto;
import com.example.tingle.home.dto.HomePictureDto;
import com.example.tingle.star.entity.StarEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "home")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordering;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "homeEntity")
    private List<HomePictureEntity> homePictureEntities;

    @ManyToOne
    private StarEntity starEntity;

    public HomeDto toDto() {
    List<HomePictureDto> homePictureDtos = new ArrayList<>();
    for (HomePictureEntity homePictureEntity : homePictureEntities) {
        homePictureDtos.add(homePictureEntity.toDto());
    }

    return HomeDto.builder()
            .starId(starEntity.getId())
            .id(id)
            .ordering(ordering)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .content(content)
            .homePictureDtos(homePictureDtos)
            .build();
}


}


