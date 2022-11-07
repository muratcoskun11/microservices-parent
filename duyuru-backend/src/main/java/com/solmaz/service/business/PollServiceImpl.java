package com.solmaz.service.business;

import com.solmaz.entity.Poll;
import com.solmaz.repository.PollRepository;
import com.solmaz.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;


    @Override
    public Poll createPoll(Poll poll) {
        return pollRepository.saveAndFlush(poll);
    }
    @Override
    public Poll findById(String id) {
        return pollRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("poll not found"));
    }
}
