package org.sam.melchor.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CommentRequest {

    @NotNull
    private Long postId;

    @NonNull
    private String category;

    private String email;

    private String nonMemberName;

    private String nonMemberPwd;

    @NotNull
    private String content;


}
