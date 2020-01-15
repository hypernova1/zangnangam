package org.sam.melchor.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter @Setter
public class Account extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Length(max = 30)
    @NotBlank
    @Column(unique = true)
    private String email;

    @Length(max = 20)
    @NotBlank
    private String name;

    @NotBlank
    private String password;

}
