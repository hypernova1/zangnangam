package org.sam.melchor.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Lob
    private String content;

//    @NotBlank
//    @ManyToOne
//    private Account writer;

}
