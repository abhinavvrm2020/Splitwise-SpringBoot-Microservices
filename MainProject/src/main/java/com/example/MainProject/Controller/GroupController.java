package com.example.MainProject.Controller;

import com.example.MainProject.Schema.Group.Groups;
import com.example.MainProject.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("create")
    public ResponseEntity<Groups> createGroup(
            @RequestBody Groups groupInfo
    ) {
        return ResponseEntity.ok(groupService.createGroup(groupInfo));
    }

    @PostMapping("add/{groupId}")
    public ResponseEntity<Groups> addMember (
            @PathVariable Integer groupId,
            @RequestBody Integer userId
    ) {
        return ResponseEntity.ok(groupService.addMember(groupId,userId));
    }

}
