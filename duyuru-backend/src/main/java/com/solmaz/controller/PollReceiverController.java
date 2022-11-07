package com.solmaz.controller;

import com.solmaz.dto.request.AddPollRequest;
import com.solmaz.dto.request.ChoosePollOptionRequest;
import com.solmaz.dto.response.AddPollResponse;
import com.solmaz.dto.response.ChoosePollOptionResponse;
import com.solmaz.dto.response.GetPollResponse;
import com.solmaz.service.PollReceiverService;
import com.solmaz.security.service.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pollReceiver")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin
public class PollReceiverController {
    private final PollReceiverService pollReceiverService;
    private final TokenProvider tokenProvider;
    @PostMapping("/publishPoll")
    public AddPollResponse publishPoll(@RequestBody AddPollRequest addPollRequest){
        final var userId = tokenProvider.getUserIdFromRequest();
        return pollReceiverService.publishPoll(userId,addPollRequest);
    }
    @PostMapping("/getReceivedPolls")
    public GetPollResponse getReceivedPolls(){
        final var userId = tokenProvider.getUserIdFromRequest();
        return pollReceiverService.getReceivedPolls(userId);
    }
    @PostMapping("/choosePollOption")
    public ChoosePollOptionResponse choosePollOption(@RequestBody ChoosePollOptionRequest choosePollOptionRequest){
        final var userId = tokenProvider.getUserIdFromRequest();
        return pollReceiverService.choosePollOption(userId,choosePollOptionRequest);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception){
        return exception.getMessage();
    }
}
