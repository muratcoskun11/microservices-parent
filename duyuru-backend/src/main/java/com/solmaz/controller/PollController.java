package com.solmaz.controller;

import com.solmaz.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/poll")
@RequestScope
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;


}
