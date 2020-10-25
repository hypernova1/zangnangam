package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class PostRequest {

    private Long id;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;
    @NotBlank(message = "작성자는 필수입니다.")
    private String writer;

}
