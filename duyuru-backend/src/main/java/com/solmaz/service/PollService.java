package com.solmaz.service;

import com.solmaz.entity.Poll;

public interface PollService {
    Poll createPoll(Poll poll);

    Poll findById(String id);
}
