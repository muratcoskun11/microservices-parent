package com.solmaz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveDirectoryAuthenticationResponse {
    private boolean success;
    private String fullname;
}
