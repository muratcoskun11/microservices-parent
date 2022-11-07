package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollReceiverResponse {
    private final String id;
    private final UserResponse userResponse;
}
