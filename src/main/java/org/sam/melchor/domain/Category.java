package org.sam.melchor.domain;

import lombok.*;
import org.sam.melchor.domain.audit.DateAudit;
import org.sam.melchor.web.payload.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Category extends DateAudit {

    @Id @GeneratedValue
    private Long id;

    private int orderNo;

    private String name;

    private String path;

    private String role;

    @Builder
    public Category(int orderNo, String name, String path, String role) {
        this.orderNo = orderNo;
        this.name = name;
        this.path = path;
        this.role = role;
    }

    public void update(CategoryDto request) {
        this.orderNo = request.getOrderNo();
        this.name = request.getName();
        this.path = request.getPath();
        this.role = request.getRole();
    }
}

