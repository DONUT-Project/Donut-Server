package com.donut.donut.service.done;

import com.donut.donut.payload.request.UpdateDoneRequest;
import com.donut.donut.payload.request.WriteDoneRequest;
import com.donut.donut.payload.response.DoneResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoneService {
    List<DoneResponse> readDone(String token);
    List<DoneResponse> searchDoneByWriteAt(String token, LocalDate writeAt);
    List<DoneResponse> readFriendDone(String token, Long friendId);
    void writeDone(String token, WriteDoneRequest writeDoneRequest);
    void updateDone(String token, Long doneId, UpdateDoneRequest updateDoneRequest);
    void updatePublic(String token, Long doneId, Boolean isPublic);
    void deleteDone(String token, Long doneId);
}
