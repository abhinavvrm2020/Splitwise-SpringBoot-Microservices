package com.example.MainProject.Controller;

import com.example.MainProject.Service.SettlementService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("settlements")
public class SettlementController {
    @Autowired
    public SettlementService settlementService;
    @PostMapping("group/{group_id}/payer/{payer_id}/receiver/{receiver_id}")                    // id -> Current Settlement id which is being tried to update.
    public ResponseEntity<String> doSettlement(
            @PathVariable Integer group_id,
            @PathVariable Integer payer_id,
            @PathVariable Integer receiver_id,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(settlementService.doSettlement(group_id,payer_id,receiver_id,authHeader));
    }
}
