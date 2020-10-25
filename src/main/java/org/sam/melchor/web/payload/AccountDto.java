package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

public class AccountDto {

    @Getter
    @Setter
    @ToString
    public static class UpdateRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateResponse {
        private String email;
        private String name;
    }

    @Getter @Setter
    @ToString
    public static class SummaryResponse {

        private Long id;
        private String email;
        private String name;
        private String role;

    }

}
