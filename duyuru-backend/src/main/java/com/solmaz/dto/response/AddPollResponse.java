package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class AddPollResponse {
    private PollResponse pollResponse;
    private List<PollReceiverResponse> pollReceiversResponse;
}
