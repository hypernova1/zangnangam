package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {

    private Long id;
    private int orderNo;
    private String name;
    private String path;
    private String role;

}
