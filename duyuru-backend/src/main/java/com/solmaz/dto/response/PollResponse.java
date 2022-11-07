package com.solmaz.dto.response;

import com.solmaz.entity.LiveTime;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PollResponse {
    private final String id;
    private final String question;
    private final LiveTime liveTime;
    private final List<PollOptionResponse> pollOptionResponse;
}
