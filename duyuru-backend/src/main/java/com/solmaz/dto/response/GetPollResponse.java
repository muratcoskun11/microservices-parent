package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class GetPollResponse {
    private final List<PollResponse> pollResponseList;

}
