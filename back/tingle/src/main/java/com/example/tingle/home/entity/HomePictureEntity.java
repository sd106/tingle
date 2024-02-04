package com.example.tingle.home.entity;

import com.example.tingle.home.dto.HomePictureDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "homePicture")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ordering;

    private String image;

    @Setter
    @ManyToOne
    private HomeEntity homeEntity;


    public HomePictureDto toDto() {
        return HomePictureDto.builder()
                .homeId(homeEntity.getId())
                .ordering(ordering)
                .image(image)
                .build();
    }

}


