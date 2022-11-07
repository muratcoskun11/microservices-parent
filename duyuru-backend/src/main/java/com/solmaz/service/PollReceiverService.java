package com.solmaz.service;

import com.solmaz.dto.request.AddPollRequest;
import com.solmaz.dto.request.ChoosePollOptionRequest;
import com.solmaz.dto.response.AddPollResponse;
import com.solmaz.dto.response.ChoosePollOptionResponse;
import com.solmaz.dto.response.GetPollResponse;

public interface PollReceiverService {
    AddPollResponse publishPoll(String userId,AddPollRequest addPollRequest);

    GetPollResponse getReceivedPolls(String id);

    ChoosePollOptionResponse choosePollOption(String userId,ChoosePollOptionRequest choosePollOptionRequest);
}
