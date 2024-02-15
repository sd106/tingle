package com.example.tingle.snapshot.controller;

import com.example.tingle.snapshot.dto.request.LikeRequest;
import com.example.tingle.snapshot.dto.request.SnapShotDetailRequest;
import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.CommentEntity;
import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.entity.SnapShotTag;
import com.example.tingle.snapshot.service.LikeServiceimpl;
import com.example.tingle.snapshot.service.SnapShotServiceImpl;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/snapshot")
@RequiredArgsConstructor
public class SnapShotController {

    private final SnapShotServiceImpl snapShotServiceImpl;
    private final LikeServiceimpl likeServiceimpl;
    private final UserServiceImpl userServiceimpl;


    @GetMapping("/star/{starId}/created")
    public ResponseEntity<Map<String, Object>> getSnapShotOrderByCreateAt(@PathVariable Long starId) {

        System.out.println("스냅샷 불러올게요");
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<SnapShotEntity> allSnapShot = snapShotServiceImpl.getAllSnapShotsByCreatedTime(starId);

        System.out.println("allSnapShot = " + allSnapShot);

        List<Map<String, Object>> snapShotList = allSnapShot.stream().map(snapShot -> {
            Map<String, Object> snapShotMap = new HashMap<>();
            snapShotMap.put("id", snapShot.getId());
            snapShotMap.put("imageUrl", snapShot.getImageUrl());
            snapShotMap.put("isStarLike", snapShot.isStarLike());
            System.out.println("snapShot = " + snapShot.isStarLike());
            UserEntity user = snapShot.getUser();
            snapShotMap.put("username", user != null ? user.getUsername() : "Unknown");
            System.out.println("snapShotMap = " + snapShotMap);

            return snapShotMap;
        }).collect(Collectors.toList());

        resultMap.put("AllSnapShot", snapShotList);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/star/{starId}/likes")
    public ResponseEntity<Map<String, Object>> getSnapShotOrderBylikes(@PathVariable Long starId) {

        System.out.println("좋아요순 스냅샷 불러올게요");
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<SnapShotEntity> allSnapShot = snapShotServiceImpl.getAllSnapShotOrderByLikes(starId);

        System.out.println("allSnapShot = " + allSnapShot);

        List<Map<String, Object>> snapShotList = allSnapShot.stream().map(snapShot -> {
            Map<String, Object> snapShotMap = new HashMap<>();
            snapShotMap.put("id", snapShot.getId());
            snapShotMap.put("imageUrl", snapShot.getImageUrl());
            snapShotMap.put("isStarLike", snapShot.isStarLike());
            System.out.println("snapShot.isStarLike = " + snapShot.isStarLike());
            UserEntity user = snapShot.getUser();
            snapShotMap.put("username", user != null ? user.getUsername() : "Unknown");
            System.out.println("snapShotMap = " + snapShotMap);

            return snapShotMap;
        }).collect(Collectors.toList());

        resultMap.put("AllSnapShot", snapShotList);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/{snapshotId}")
    public ResponseEntity<Map<String, Object>> SnapShotDetail(@PathVariable Long snapshotId,
                                                              @RequestBody SnapShotDetailRequest snapShotDetailRequest)
            throws IOException {
        System.out.println("snapShotDetailRequest = " + snapShotDetailRequest.getUsername());
        System.out.println("snapShotDetailRequest = " + snapShotDetailRequest.getIsStarLogin());
//        String json = username;
//
//// ObjectMapper 인스턴스 생성
//        ObjectMapper mapper = new ObjectMapper();
//
//// JSON 문자열을 JsonNode 객체로 변환
//        JsonNode rootNode = mapper.readTree(json);
//
//// "username" 필드의 값을 추출
//        String name = rootNode.get("username").asText();
//
//        System.out.println("상세 스냅샷의 username = " + name);
//
        System.out.println("상세 스냅샷 불러올게요");
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String name = snapShotDetailRequest.getUsername();
        Optional<SnapShotEntity> optSnapShotEntity = snapShotServiceImpl.getSnapShotById(snapshotId);
        System.out.println("optSnapShotEntity = " + optSnapShotEntity.get().getId());
        if (optSnapShotEntity.isPresent()) {

            SnapShotEntity snapShot = optSnapShotEntity.get();
            Boolean isStarLogin = snapShotDetailRequest.getIsStarLogin();
            boolean isLike = likeServiceimpl.findLike(isStarLogin, name, snapShot);

            List<String> tags = snapShot.getSnapShotTags().stream()
                    .map(SnapShotTag::getHashTagEntity)
                    .map(HashTagEntity::getTag)
                    .collect(Collectors.toList());

            List<CommentEntity> comments = snapShot.getComments();



            List<Map<String, Object>> commentMaps = new ArrayList<>();
            for (CommentEntity comment : comments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", comment.getId());
                commentMap.put("context", comment.getContext());
                commentMap.put("username", comment.getUsername());
                commentMap.put("snapshotId", comment.getSnapShotEntity().getId());
                commentMap.put("isStar", comment.getIsStar());
                commentMap.put("createdAt", comment.getCreatedAt());
                commentMaps.add(0, commentMap);
            }
            /**
             *  위의 코멘트정보를 하나의 해쉬맵에 담고 그걸 리스트에 넣어줘야한다.
             */

            resultMap.put("snapshotId", snapShot.getId());
            resultMap.put("imageUrl", snapShot.getImageUrl());
            resultMap.put("username", snapShot.getUser() != null ? snapShot.getUser().getUsername() : "Unknown User");
            resultMap.put("starname", snapShot.getStar() != null ? snapShot.getStar().getUsername() : "Unknown Star");
            resultMap.put("content", snapShot.getContent());
            resultMap.put("tags", tags);
            resultMap.put("comments", commentMaps);
            resultMap.put("likes", snapShot.getLikesCount());
            resultMap.put("createdAt", snapShot.getCreatedAt());
            resultMap.put("updatedAt", snapShot.getUpdatedAt());
            resultMap.put("isLiked", isLike);

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
                                                           @RequestParam("starId") Long starId)
            throws IOException {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        System.out.println("스냅샷 만들게요");

        SnapShotRequest snapShotRequest = SnapShotRequest.builder()
                .content(content)
                .tags(tags)
                .username(username)
                .starId(starId)
                .build();

        snapShotServiceImpl.uploadSnapshot(snapShotRequest, file);

        resultMap.put("result", "성공해쓰!!");

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/{snapshotId}/likes")
    public ResponseEntity<Map<String, Object>> LikeSnapShot(@PathVariable Long snapshotId,
                                                            @RequestBody LikeRequest likeRequest)
            throws IOException {
        System.out.println("좋아요 시작");
        System.out.println("isStar = " + likeRequest.getIsStar());
        System.out.println("username = " + likeRequest.getUsername());

        likeServiceimpl.addLike(likeRequest.getIsStar(), likeRequest.getUsername(), snapshotId);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/{snapshotId}/dislikes")
    public ResponseEntity<?> dislikeSnapshot(@PathVariable Long snapshotId,
                                             @RequestBody LikeRequest likeRequest)
            throws IOException {
        System.out.println("좋아요 취소 시작");
        System.out.println("isStar = " + likeRequest.getIsStar());
        System.out.println("username = " + likeRequest.getUsername());

        likeServiceimpl.removeLike(likeRequest.getIsStar(), likeRequest.getUsername(), snapshotId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{snapshotId}/update")
    public ResponseEntity<Map<String, Object>> updateSnapShot(@PathVariable Long snapshotId,
                                                              @RequestParam("file") MultipartFile file,
                                                              @RequestParam("content") String content,
                                                              @RequestParam("tags") List<String> tags)
            throws IOException {

        System.out.println("수정 시작합니다.");

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

    @DeleteMapping("/{snapshotId}/delete")
    public ResponseEntity<Map<String, Object>> deleteSnapShot(@PathVariable Long snapshotId, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        snapShotServiceImpl.deleteSnapShot(snapshotId);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
