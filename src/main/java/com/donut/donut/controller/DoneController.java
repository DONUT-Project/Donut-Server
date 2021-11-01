package com.donut.donut.controller;

import com.donut.donut.payload.request.UpdateDoneRequest;
import com.donut.donut.payload.request.WriteDoneRequest;
import com.donut.donut.payload.response.DoneResponse;
import com.donut.donut.service.done.DoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/done")
@RequiredArgsConstructor
public class DoneController {

    private final DoneService doneService;

    @GetMapping
    public List<DoneResponse> getMyDone(@RequestHeader("Authorization") String token) {
        return doneService.readDone(token);
    }

    @GetMapping("/friend/{friendId}")
    public List<DoneResponse> getFriendDone(@RequestHeader("Authorization") String token,
                                            @PathVariable Long friendId) {
        return doneService.readFriendDone(token, friendId);
    }

    @GetMapping("/search")
    public List<DoneResponse> searchWriteAt(@RequestHeader("Authorization") String token,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate writeAt) {
        return doneService.searchDoneByWriteAt(token, writeAt);
    }

    @PostMapping
    public void writeDone(@RequestHeader("Authorization") String token,
                          @RequestBody WriteDoneRequest writeDoneRequest) {
        doneService.writeDone(token, writeDoneRequest);
    }

    @PutMapping("/{doneId}")
    public void updateDone(@RequestHeader("Authorization") String token,
                           @RequestBody UpdateDoneRequest updateDoneRequest,
                           @PathVariable Long doneId) {
        doneService.updateDone(token, doneId, updateDoneRequest);
    }

    @DeleteMapping("/{doneId}")
    public void deleteDone(@RequestHeader("Authorization") String token,
                           @PathVariable Long doneId) {
        doneService.deleteDone(token, doneId);
    }
}
