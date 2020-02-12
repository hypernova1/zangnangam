package org.sam.melchor.domain;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Entity
public class Category extends DateAudit {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private Integer orderNo;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

    @NotNull
    private String role;


}
