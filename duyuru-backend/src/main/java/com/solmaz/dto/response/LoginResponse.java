package com.solmaz.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -7091479091924046844L;
    private String token;
    private String id;
}
