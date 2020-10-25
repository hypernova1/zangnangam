package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class AuthDto {

    @Getter
    @Setter
    @ToString
    public static class SignUpRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String name;
        @NotBlank
        private String password;
    }

    @Getter @Setter
    public static class LoginRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }



}
