package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.AccountDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Length(max = 30)
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @Column(unique = true)
    private String email;

    @Length(max = 20)
    private String name;

    private String password;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    private Set<Role> roles;

    public void addPost(Post post) {
        post.setWriter(this);
        posts.add(post);
    }

    public void update(AccountDto.UpdateRequest request) {
        this.name = request.getName();
        this.password = request.getPassword();
    }
}
