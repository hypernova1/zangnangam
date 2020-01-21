package org.sam.melchor.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {

    private Long id;
    private String email;
    private String name;

}
