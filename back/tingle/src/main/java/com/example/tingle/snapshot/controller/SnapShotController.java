package com.example.tingle.snapshot.controller;

import com.example.tingle.snapshot.dto.request.SnapShotRequest;
import com.example.tingle.snapshot.dto.request.SnapShotUpdateRequest;
import com.example.tingle.snapshot.entity.SnapShotEntity;
import com.example.tingle.snapshot.repository.SnapShotRepository;
import com.example.tingle.snapshot.service.SnapShotServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
@RequestMapping("/snapshot")
@RequiredArgsConstructor
public class SnapShotController {

    private final SnapShotServiceImpl snapShotServiceImpl;
    private final SnapShotRepository snapShotRepository;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getSnapShot(@PathVariable HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<SnapShotEntity> allSnapShot = snapShotServiceImpl.getAllSnapShot();

        resultMap.put("AllSnapShot", allSnapShot);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    // getSnapShotsByCreatedTime

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newSnapShot(@RequestBody SnapShotRequest snapShotRequest) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        snapShotServiceImpl.uploadSnapshot(snapShotRequest);

        resultMap.put("result", "성공해쓰!!");

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @PostMapping("/{snapshotId}/update")
    public ResponseEntity<Map<String, Object>> updateSnapShot(@PathVariable Long snapshotId, @RequestBody SnapShotUpdateRequest snapShotUpdateRequest, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        snapShotServiceImpl.updateSnapShot(snapshotId, snapShotUpdateRequest);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{snapshotId}/delete")
    public ResponseEntity<Map<String, Object>> deleteSnapShot(@PathVariable Long snapshotId, HttpServletResponse response) {

        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        snapShotServiceImpl.deleteSnapShot(snapshotId);

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


}
