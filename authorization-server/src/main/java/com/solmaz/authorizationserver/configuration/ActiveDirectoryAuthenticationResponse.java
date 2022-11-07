package com.solmaz.authorizationserver.configuration;

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
