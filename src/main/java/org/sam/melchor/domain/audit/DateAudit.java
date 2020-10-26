package org.sam.melchor.domain.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter @Setter
public abstract class DateAudit {

    @CreatedDate
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime updated;

}

