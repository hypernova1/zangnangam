package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CommentRequest {

    @NotNull
    private Long postId;

    private String email;

    private String nonMemberName;

    private String nonMemberPwd;

    @NotBlank
    private String content;

}