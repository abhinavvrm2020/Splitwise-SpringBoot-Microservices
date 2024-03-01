package com.example.MainProject.Repository;

import com.example.MainProject.Schema.Group.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepository extends JpaRepository<Groups,Integer> {
    @Query(value = "SELECT * FROM groups WHERE admin_id = :payerId", nativeQuery = true)
    List<Groups> findByPayerId(Integer payerId);
}
