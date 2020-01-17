package org.sam.melchor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnore
    private Account writer;

    @ManyToOne
    @JsonIgnore
    private Post post;
}
