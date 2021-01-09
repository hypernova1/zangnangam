package org.sam.melchor.domain;

import lombok.*;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.AccountDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

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

    public boolean isAdmin() {
        return this.roles.stream()
                .anyMatch(role -> role.getName() == RoleName.ROLE_ADMIN);
    }
}
