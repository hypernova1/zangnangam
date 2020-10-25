package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Post;

@Getter @Setter
public class CommentResponse {

    private Long id;
    private String content;
    private Account writer;
    private Post post;
    private String nonMemberName;
    private String nonMemberPwd;
    private String created;

}
