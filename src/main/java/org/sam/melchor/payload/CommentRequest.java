package org.sam.melchor.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CommentRequest {

    @NotNull
    private Long postId;

    @NotNull
    private String categoryPath;

    private String email;

    private String nonMemberName;

    private String nonMemberPwd;

    @NotBlank
    private String content;


}
