package com.example.MainProject.Service;

import com.example.MainProject.Repository.GroupRepository;
import com.example.MainProject.Schema.Group.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    public Groups createGroup(Groups groupInfo) {
        Groups newGroup = Groups.builder()
                .groupId(groupInfo.getGroupId())
                .name(groupInfo.getName())
                .adminId(groupInfo.getAdminId())
                .members(groupInfo.getMembers()).build();
        return groupRepository.save(newGroup);
    }

    public Groups addMember (Integer groupId, Integer userId) {
        Groups currentGroup = groupRepository.findById(groupId)
                .orElseThrow();
        List<Integer> currentUsers = currentGroup.getMembers();
        boolean isPresent = currentUsers.contains(userId);
        if(!isPresent) {
            currentUsers.add(userId);
            currentGroup.setMembers(currentUsers);
            return groupRepository.save(currentGroup);
        } else {
            throw new RuntimeException("User already in group");
        }
    }

}
