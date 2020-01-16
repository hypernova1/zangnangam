package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.*;

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
    @JsonBackReference
    private Account writer;

    @ManyToOne
    private Post post;
}
