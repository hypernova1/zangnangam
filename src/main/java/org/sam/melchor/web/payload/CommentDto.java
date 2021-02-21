package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentDto {

    @Getter @Setter
    public static class Request {
        @NotNull
        private Long postId;
        private String email;
        private String nonMemberName;
        private String nonMemberPwd;
        @NotBlank
        private String content;
    }

    @Getter @Setter
    public static class Response {
        private Long id;
        private String content;
        private AccountDto.SummaryResponse writer;
        private String nonMemberName;
        private String nonMemberPwd;
        private String created;
    }

}
