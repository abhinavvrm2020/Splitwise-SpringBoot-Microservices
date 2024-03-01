package com.example.MainProject.Schema.Group;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Groups {
    @Id //payerId -> primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
    private String name;
    private Integer adminId;
    private List<Integer> members;
}
