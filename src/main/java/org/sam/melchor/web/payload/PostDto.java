package org.sam.melchor.web.payload;

import lombok.Getter;
import lombok.Setter;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostDto {

    @Getter @Setter
    public static class RegisterRequest {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;
        @NotBlank(message = "내용은 필수입니다.")
        private String content;
        @NotNull(message = "카테고리는 필수입니다.")
        private Long categoryId;
        @NotBlank(message = "작성자는 필수입니다.")
        private String writer;
    }

    @Getter @Setter
    public static class SummaryResponse {
        private Long id;
        private String title;
        private String writer;
        private String created;
    }

    @Getter @Setter
    public static class UpdateRequest {
        private Long id;
        private String title;
        private String content;
        private Long categoryId;
    }

    @Getter @Setter
    public static class DetailResponse {
        private Long id;
        private String title;
        private String content;
        private int LikeCnt;
        private List<CommentDto.Response> commentList;
    }

    @Getter @Setter
    public static class ListResponse {
        private String categoryName;
        private List<SummaryResponse> postList;
        private boolean isNext;
    }


}


