package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private String postId;

    private String topic;

    private String content;

    private LocalDateTime time;

    private Creator creator;
    private int numberOfReceivers;
    private List<String> attachements;
}
