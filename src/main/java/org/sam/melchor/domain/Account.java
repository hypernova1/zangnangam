package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Account extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Length(max = 30)
    @NotBlank
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Column(unique = true)
    private String email;

    @Length(max = 20)
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "writer")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        post.setWriter(this);
        posts.add(post);
    }

}
