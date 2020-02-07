package org.sam.melchor.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CommentRequest {

    private Long postId;

    private String email;

    private String nonMemberName;

    private String nonMemberPwd;

    private String content;

}
