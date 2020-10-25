package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CategoryDto {

    private Long id;
    private Integer orderNo;
    private String name;
    private String path;
    private String role;

}
