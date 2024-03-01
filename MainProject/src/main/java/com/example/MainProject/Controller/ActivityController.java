package com.example.MainProject.Controller;

import com.example.MainProject.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @GetMapping("receive")
    public ResponseEntity<Integer> getReceiveAmount (
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(activityService.getReceiveAmount(authHeader));
    }

    @GetMapping("send")
    public ResponseEntity<Integer> getSendAmount (
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(activityService.getSendAmount(authHeader));
    }

}
