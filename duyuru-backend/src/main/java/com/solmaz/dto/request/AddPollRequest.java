package com.solmaz.dto.request;

import com.solmaz.entity.LiveTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPollRequest {
    private String question;
    private LiveTime liveTime;
    private List<String> pollOptions;
    private List<String> userIdList;
    private List<String> groupIdList;
}
