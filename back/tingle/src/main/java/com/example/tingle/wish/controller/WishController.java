package com.example.tingle.wish.controller;

import com.example.tingle.wish.dto.request.WishRequest;
import com.example.tingle.wish.dto.response.WishResponse;
import com.example.tingle.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @GetMapping("/read/{starId}")
    public ResponseEntity<Map<String, Object>> readWish(@PathVariable Long starId) {
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            List<WishResponse> list = wishService.read(starId);

            if(list.isEmpty())
                resultMap.put("read", "list 비어 있음...");
            else
                resultMap.put("read", list);
        } catch (Exception e) {
            resultMap.put("msg", "error 발생!");
            e.printStackTrace();
        }

        return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveWish(@RequestBody WishRequest wishRequest) {
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            wishService.saveWish(wishRequest);
            resultMap.put("save", "save 성공!");
        } catch(Exception e) {
            resultMap.put("msg", "error 발생!");
            e.printStackTrace();
        }

        return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }

    /* 수정 기능 미구현 */
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateWish(@RequestParam WishRequest wishRequest) {
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            wishService.updateWish(wishRequest);
            resultMap.put("update", "update 성공!");
        } catch(Exception e) {
            resultMap.put("msg", "error 발생!");
            e.printStackTrace();
        }

        return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteWish(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            wishService.deleteWish(id);
            resultMap.put("delete", "delete 성공!");
        } catch(Exception e) {
            resultMap.put("msg", "error 발생!");
            e.printStackTrace();
        }

        return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }
}
