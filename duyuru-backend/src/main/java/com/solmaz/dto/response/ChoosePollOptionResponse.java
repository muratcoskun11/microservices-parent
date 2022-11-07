package com.solmaz.dto.response;

public record ChoosePollOptionResponse(String userId, String pollId, String selectedOption, long numberOfSelectors) {
}
