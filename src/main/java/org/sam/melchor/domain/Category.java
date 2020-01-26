package org.sam.melchor.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.sam.melchor.domain.audit.DateAudit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Category extends DateAudit {

    @Id @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String path;

    @NonNull
    private Integer depth;

    @NonNull
    private Integer orderNo;

    private Integer parentId;

}
