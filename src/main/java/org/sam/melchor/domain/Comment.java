package org.sam.melchor.domain;

import lombok.NonNull;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

public class Comment extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @NonNull
    private String comment;

    @ManyToOne
    private Account writer;

    @ManyToOne
    private Post post;
}
