package com.donut.donut.service.done;

import com.donut.donut.payload.request.UpdateDoneRequest;
import com.donut.donut.payload.request.WriteDoneRequest;
import com.donut.donut.payload.response.DoneResponse;

import java.util.List;

public interface DoneService {
    List<DoneResponse> readDone(String token);
    List<DoneResponse> readFriendDone(String token, Long friendId);
    void writeDone(String token, WriteDoneRequest writeDoneRequest);
    void updateDone(String token, Long doneId, UpdateDoneRequest updateDoneRequest);
    void deleteDone(String token, Long doneId);
}
