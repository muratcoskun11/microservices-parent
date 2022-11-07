package com.solmaz.service.business;

import com.solmaz.dto.request.AddPollRequest;
import com.solmaz.dto.request.ChoosePollOptionRequest;
import com.solmaz.dto.response.*;
import com.solmaz.repository.GroupPollReceiverRepository;
import com.solmaz.repository.PollOptionRepository;
import com.solmaz.repository.SelectedOptionRepository;
import com.solmaz.repository.UserPollReceiverRepository;
import com.solmaz.service.GroupService;
import com.solmaz.service.PollReceiverService;
import com.solmaz.service.PollService;
import com.solmaz.service.UserService;
import com.solmaz.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PollReceiverServiceImpl implements PollReceiverService {
    private final UserPollReceiverRepository userPollReceiverRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final PollService pollService;
    private final ModelMapper modelMapper;
    private final PollOptionRepository pollOptionRepository;
    private final GroupPollReceiverRepository groupPollReceiverRepository;
    private final SelectedOptionRepository selectedOptionRepository;
    @Override
    public AddPollResponse publishPoll(String userId, AddPollRequest addPollRequest) {
        var poll = modelMapper.map(addPollRequest, Poll.class);
        poll.setPublisher(userService.findById(userId));
        var savedPoll = pollService.createPoll(poll);

        //Poll Options and poll saved
        var pollOptions = addPollRequest.getPollOptions().stream().map(option-> {
            var pollOption = new PollOption();
            pollOption.setOption(option);
            pollOption.setPoll(savedPoll);
           return pollOption;
        }).toList();
        var savedPollOptions = pollOptionRepository.saveAll(pollOptions);
        //Poll sent to users
        var userList = userService.findAllById(addPollRequest.getUserIdList());
        var userPollReceiverList = userList.stream().map(user -> {
            var userPollReceiver =new UserPollReceiver();
            userPollReceiver.setPoll(savedPoll);
            userPollReceiver.setUser(user);
            var savedUserPollReceiver = userPollReceiverRepository.save(userPollReceiver);
            return savedUserPollReceiver;
        }).toList();
        //Poll sent to Group
        var groupList = groupService.findAllById(addPollRequest.getGroupIdList());
        groupList.forEach(group -> {
            var groupPollReceiver = new GroupPollReceiver();
            groupPollReceiver.setGroup(group);
            groupPollReceiver.setPoll(savedPoll);
            groupPollReceiverRepository.save(groupPollReceiver);
        });
        var pollReceiverList = new ArrayList<PollReceiverResponse>();
        var userPollReceivers  =userPollReceiverList.stream().map(userPollReceiver -> new PollReceiverResponse(userPollReceiver.getPollReceiverId(),modelMapper.map(userPollReceiver.getUser(), UserResponse.class))).toList();
        var groupmemberPollReceivers = groupList.stream().map(Group::getGroupMemberList).toList().stream().map(groupMemberList -> groupMemberList.stream().map(groupMember -> modelMapper.map(groupMember, GroupMemberResponse.class)).toList()).toList();
        var pollReceiverResponsesList = groupmemberPollReceivers.stream().map(groupMemberResponses -> groupMemberResponses.stream().map(groupMemberResponse -> new PollReceiverResponse(groupMemberResponse.getGroupMemberId(),groupMemberResponse.getMember())).toList()).toList();
        pollReceiverResponsesList.forEach(pollReceiverList::addAll);
        pollReceiverList.addAll(userPollReceivers);
        return new AddPollResponse(new PollResponse(savedPoll.getPollId(), savedPoll.getQuestion(), savedPoll.getLiveTime(),savedPollOptions.stream().map(pollOption -> new PollOptionResponse(pollOption.getId(), pollOption.getOption(), pollOption.getSelectedOptions().size())).toList()),pollReceiverList);
    }

    @Override
    public GetPollResponse getReceivedPolls(String userId) {
        var user = userService.findById(userId);
        var responseList = new ArrayList<PollResponse>();
        var receivedPollsByUser = user.getUserPollReceiver().stream().map(UserPollReceiver::getPoll).toList();
        var receivedByUserPollResponseList = receivedPollsByUser.stream().map(poll -> modelMapper.map(poll,PollResponse.class)).toList();
        var receivedByGroupPollResponseList = user.getGroupMembers().stream().map(groupMember -> groupMember.getGroup().getGroupPollReceivers().stream().map(GroupPollReceiver::getPoll).toList()).toList().stream().map(polls -> polls.stream().map(poll -> modelMapper.map(poll,PollResponse.class)).toList()).toList();
        var userOwnPolls = user.getPolls().stream().map(poll -> modelMapper.map(poll,PollResponse.class)).toList();
        responseList.addAll(receivedByUserPollResponseList);
        responseList.addAll(userOwnPolls);
        receivedByGroupPollResponseList.forEach(responseList::addAll);
        return new GetPollResponse(responseList);
    }

    @Override
    public ChoosePollOptionResponse choosePollOption(String userId, ChoosePollOptionRequest choosePollOptionRequest) {
        var selectedOption = new SelectedOption();
        var poll = pollService.findById(choosePollOptionRequest.pollId());
        var pollOption = poll.getPollOptions().stream().filter(entity -> entity.getOption().equals(choosePollOptionRequest.option())).toList().get(0);
        selectedOption.setOption(pollOption);
        selectedOption.setSelector(userService.findById(userId));
        selectedOptionRepository.save(selectedOption);
        var numberOfSelectors = pollOption.getSelectedOptions().size();
        return new ChoosePollOptionResponse(userId,poll.getPollId(), choosePollOptionRequest.option(), numberOfSelectors );
    }
}
