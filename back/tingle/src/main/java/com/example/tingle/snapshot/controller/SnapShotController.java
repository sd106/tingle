package com.example.tingle.snapshot.controller;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.HashTagEntity;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.entity.SnapShotTag;
import com.example.tingle.snapshot.service.SnapShotServiceImpl;
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

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<SnapShotEntity> allSnapShot = snapShotServiceImpl.getAllSnapShot();

        System.out.println(allSnapShot);

        resultMap.put("AllSnapShot", allSnapShot);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{snapshotId}")
    public ResponseEntity<Map<String, Object>> SnapShotDetail(@PathVariable Long snapshotId) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();


        resultMap.put("result", "성공해쓰!!");

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

            snapShotServiceImpl.updateSnapShot(snapshotId, snapShotUpdateRequest, file, previousImageUrl);

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
