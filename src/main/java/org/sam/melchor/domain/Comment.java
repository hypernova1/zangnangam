package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
public class Comment extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NonNull
    private String comment;

    @ManyToOne
    @NotNull
    private Account writer;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Post post;
}
