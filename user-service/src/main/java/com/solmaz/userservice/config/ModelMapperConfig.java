package com.solmaz.config;

import com.solmaz.dto.request.AddPostRequest;
import com.solmaz.dto.request.AddPollRequest;
import com.solmaz.dto.request.AddPrivatePostRequest;
import com.solmaz.dto.response.*;
import com.solmaz.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ModelMapperConfig {
    private static final Converter<AddPostRequest, Post> addPostRequestPostConverter = context -> {
        var addPostRequest = context.getSource();
        var post = new Post();
        post.setTopic(addPostRequest.getTopic());
        post.setContent(addPostRequest.getContent());
        return post;
    };
    private static final Converter<AddPrivatePostRequest, PrivatePost> ADD_PRIVATE_POST_REQUEST_PRIVATE_POST_CONVERTER = context -> {
        var addPostRequest = context.getSource();
        var post = new PrivatePost();
        post.setTopic(addPostRequest.getTopic());
        post.setContent(addPostRequest.getContent());
        return post;
    };
    private static final Converter<Post, PostResponse> postToPostResponseConverter = context -> {
        var post = context.getSource();
        var user = context.getSource().getUser();
        var postResponse = PostResponse.builder().postId(post.getPostId()).content(post.getContent()).topic(post.getTopic())
                .time(post.getTime()).creator(Creator.builder().creatorId(user.getUserId()).fullName(user.getFullName())
                        .photoUrl(user.getPhotoUrl())
                        .title(user.getTitle()).build())
                .numberOfReceivers(post.getGroupPostReceivers().size()+post.getUserPostReceivers().size())
                .attachements(post.getAttachements().stream().map(attachement -> attachement.getId()).toList()).build();
        return postResponse;
    };
    private static final Converter<PrivatePost, PostResponse> PRIVATE_POST_POST_RESPONSE_CONVERTER = context -> {
        var post = context.getSource();
        var user = context.getSource().getUser();
        var postResponse = PostResponse.builder().postId(post.getPostId()).content(post.getContent()).topic(post.getTopic())
                .time(post.getTime()).creator(Creator.builder().creatorId(user.getUserId()).fullName(user.getFullName())
                        .photoUrl(user.getPhotoUrl())
                        .title(user.getTitle()).build())
                .numberOfReceivers(post.getGroupPrivatePostReceviers().size()+post.getPrivateUserPostReceivers().size())
                .attachements(post.getAttachements().stream().map(attachement -> attachement.getId()).toList()).build();
        return postResponse;
    };
    private static final Converter<User, UserResponse> userUserResponseConverter = context -> {
        var user = context.getSource();
        var userResponse = UserResponse.builder().userId(user.getUserId()).title(user.getTitle()).fullName(user.getFullName())
                .photoUrl(user.getPhotoUrl()).build();
        return userResponse;
    };
    private static final Converter<Group, GroupResponse> groupGroupResponseConverter = context -> {
        var group = context.getSource();
        var groupResponse = GroupResponse.builder().groupId(group.getGroupId()).name(group.getName()).userResponseList(group.getGroupMemberList().stream().map(GroupMember::getUser).toList().stream().map(user -> UserResponse.builder().userId(user.getUserId()).title(user.getTitle()).fullName(user.getFullName()).build()).toList()).build();

        return groupResponse;
    };
    private static final Converter<GroupMember, GroupMemberResponse> GROUP_MEMBER_GROUP_MEMBER_RESPONSE_CONVERTER = context -> {
        var groupMember = context.getSource();
        var group = groupMember.getGroup();
        var response = GroupMemberResponse.builder().groupMemberId(groupMember.getGroupMemberId()).member(UserResponse.builder().userId(groupMember.getUser().getUserId()).title(groupMember.getUser().getTitle()).fullName(groupMember.getUser().getFullName())
                .photoUrl(groupMember.getUser().getPhotoUrl()).build()).group(new GroupWithoutMembers(group.getGroupId(),group.getName())).build();

        return response;
    };
    private static final Converter<AddPollRequest, Poll> ADD_POLL_REQUEST_POLL_CONVERTER = context -> {
        var addPollRequest = context.getSource();
        var poll = new Poll();
        poll.setPollOptions(new ArrayList<>());
        poll.setQuestion(addPollRequest.getQuestion());
        poll.setLiveTime(addPollRequest.getLiveTime());
        return poll;
    };
    private static final Converter<Poll, PollResponse> POLL_POLL_RESPONSE_CONVERTER = context -> {
        var poll = context.getSource();

        var response = new PollResponse(poll.getPollId(),poll.getQuestion(),poll.getLiveTime(),poll.getPollOptions().stream().map(pollOption -> new PollOptionResponse(pollOption.getId(),pollOption.getOption(),pollOption.getSelectedOptions().size())).toList());

        return response;
    };
    private static final Converter<PollOption, PollOptionResponse> POLL_OPTION_POLL_OPTION_RESPONSE_CONVERTER = context -> {
        var pollOption = context.getSource();

        var response =new PollOptionResponse(pollOption.getId(),pollOption.getOption(),pollOption.getSelectedOptions().size());

        return response;
    };


    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.addConverter(addPostRequestPostConverter, AddPostRequest.class, Post.class);
        modelMapper.addConverter(postToPostResponseConverter, Post.class, PostResponse.class);
        modelMapper.addConverter(userUserResponseConverter, User.class, UserResponse.class);
        modelMapper.addConverter(groupGroupResponseConverter, Group.class, GroupResponse.class);
        modelMapper.addConverter(GROUP_MEMBER_GROUP_MEMBER_RESPONSE_CONVERTER, GroupMember.class, GroupMemberResponse.class);
        modelMapper.addConverter(ADD_POLL_REQUEST_POLL_CONVERTER, AddPollRequest.class, Poll.class);
        modelMapper.addConverter(POLL_POLL_RESPONSE_CONVERTER, Poll.class, PollResponse.class);
        modelMapper.addConverter(POLL_OPTION_POLL_OPTION_RESPONSE_CONVERTER, PollOption.class, PollOptionResponse.class);
        modelMapper.addConverter(ADD_PRIVATE_POST_REQUEST_PRIVATE_POST_CONVERTER, AddPrivatePostRequest.class, PrivatePost.class);
        modelMapper.addConverter(PRIVATE_POST_POST_RESPONSE_CONVERTER, PrivatePost.class, PostResponse.class);
        return modelMapper;

    }
}
