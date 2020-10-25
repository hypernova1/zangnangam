package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SignUpRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

}
