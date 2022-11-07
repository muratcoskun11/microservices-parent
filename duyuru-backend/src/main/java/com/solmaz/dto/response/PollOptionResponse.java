package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollOptionResponse {
    private final Long id;
    private final String option;
    private final long numberOfSelectors;
}
