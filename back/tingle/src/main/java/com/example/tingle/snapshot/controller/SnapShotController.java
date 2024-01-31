package com.example.tingle.snapshot.controller;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.entity.SnapShotTag;
import com.example.tingle.snapshot.service.SnapShotServiceImpl;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RequestMapping("/snapshot")
@RequiredArgsConstructor
public class SnapShotController {

    private final SnapShotServiceImpl snapShotServiceImpl;


    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getSnapShot() {

        System.out.println("스냅샷 불러올게요");
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<SnapShotEntity> allSnapShot = snapShotServiceImpl.getAllSnapShot();

        System.out.println("allSnapShot = " + allSnapShot);

        List<Map<String, Object>> snapShotList = allSnapShot.stream().map(snapShot -> {
            Map<String, Object> snapShotMap = new HashMap<>();
            snapShotMap.put("id", snapShot.getId());
            snapShotMap.put("imageUrl", snapShot.getImageUrl());

            UserEntity user = snapShot.getUser();
            snapShotMap.put("username", user != null ? user.getUsername() : "Unknown");
            System.out.println("snapShotMap = " + snapShotMap);

            return snapShotMap;
        }).collect(Collectors.toList());

        resultMap.put("AllSnapShot", snapShotList);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{snapshotId}")
    public ResponseEntity<Map<String, Object>> SnapShotDetail(@PathVariable Long snapshotId) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Optional<SnapShotEntity> optSnapShotEntity = snapShotServiceImpl.getSnapShotById(snapshotId);

        if (optSnapShotEntity.isPresent()) {

            SnapShotEntity snapShot = optSnapShotEntity.get();

            List<String> tags = snapShot.getSnapShotTags().stream()
                    .map(SnapShotTag::getHashTagEntity)
                    .map(HashTagEntity::getTag)
                    .collect(Collectors.toList());

            resultMap.put("snapshotId", snapShot.getId());
            resultMap.put("imageUrl", snapShot.getImageUrl());
            resultMap.put("username", snapShot.getUser() != null ? snapShot.getUser().getUsername() : "Unknown User");
            resultMap.put("starname", snapShot.getStar() != null ? snapShot.getStar().getUsername() : "Unknown Star");
            resultMap.put("content", snapShot.getContent());
            resultMap.put("tags", tags);
            resultMap.put("comments", snapShot.getComments());
            resultMap.put("likes", snapShot.getLikes());
            resultMap.put("createdAt", snapShot.getCreatedAt());
            resultMap.put("updatedAt", snapShot.getUpdatedAt());


        } else {
            System.out.println("스냅샷을 찾을 수 없습니다.");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newSnapShot(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("content") String content,
                                                           @RequestParam("tags") List<String> tags,
                                                           @RequestParam("username") String username,
                                                           @RequestParam("starname") String starname)
            throws IOException {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("스냅샷 만들게요");

        SnapShotRequest snapShotRequest = SnapShotRequest.builder()
                .content(content)
                .tags(tags)
                .username(username)
                .starname(starname)
                .build();

        snapShotServiceImpl.uploadSnapshot(snapShotRequest, file);

        resultMap.put("result", "성공해쓰!!");

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @PostMapping("/{snapshotId}/update")
    public ResponseEntity<Map<String, Object>> updateSnapShot(@PathVariable Long snapshotId,
                                                              @RequestParam("file") MultipartFile file,
                                                              @RequestParam("content") String content,
                                                              @RequestParam("tags") List<String> tags, HttpServletResponse response)
            throws IOException {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Optional<SnapShotEntity> snapShotEntity = snapShotServiceImpl.getSnapShotById(snapshotId);

        if (snapShotEntity.isPresent()) {
            SnapShotUpdateRequest snapShotUpdateRequest = SnapShotUpdateRequest.builder()
                    .content(content)
                    .tags(tags)
                    .build();
            SnapShotEntity snapShot = snapShotEntity.get();

            // 필요한 데이터 추출
            String previousImageUrl = snapShot.getImageUrl();

            /**
             *  스냅샷 수정
             */
            snapShotServiceImpl.updateSnapShot(snapshotId, snapShotUpdateRequest, file, previousImageUrl);


            /**
             *  스냅샷 수정 후, 수정된 스냅샷의 정보를 반환
             */
            Optional<SnapShotEntity> newOptSnapShot = snapShotServiceImpl.getSnapShotById(snapshotId);

            if (newOptSnapShot.isPresent()) {

                SnapShotEntity newSnapShot = newOptSnapShot.get();

                resultMap.put("snapshotId", newSnapShot.getId());
                resultMap.put("imageUrl", newSnapShot.getImageUrl());
                resultMap.put("username", newSnapShot.getUser() != null ? newSnapShot.getUser().getUsername() : "Unknown User");
                resultMap.put("starname", newSnapShot.getStar() != null ? newSnapShot.getStar().getUsername() : "Unknown Star");
                resultMap.put("content", newSnapShot.getContent());
                resultMap.put("comments", newSnapShot.getComments());
                resultMap.put("likes", newSnapShot.getLikes());
                resultMap.put("createdAt", newSnapShot.getCreatedAt());
                resultMap.put("updatedAt", newSnapShot.getUpdatedAt());

            } else {
                System.out.println("스냅샷을 찾을 수 없습니다.");
            }
        } else {
            // 스냅샷 엔티티가 존재하지 않는 경우의 처리
            System.out.println("스냅샷을 찾을 수 없습니다.");
        }
        
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/{snapshotId}/delete")
    public ResponseEntity<Map<String, Object>> deleteSnapShot(@PathVariable Long snapshotId, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        snapShotServiceImpl.deleteSnapShot(snapshotId);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
