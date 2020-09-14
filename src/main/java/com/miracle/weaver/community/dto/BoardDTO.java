package com.miracle.weaver.community.dto;

import com.miracle.weaver.community.entity.CategoryEntity;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class BoardDTO {

    @Data
    public static class List {

        private int id;

        private String title;

        private String content;
    }

    @Data
    public static class Detail {

        private int id;

        private String title;

        private String content;

        private Set<CommentDTO.List> comments;
    }

    @Data
    public static class Create {

        @NotNull
        @NotEmpty
        private String title;

        @NotNull
        @NotEmpty
        private String content;

        @NotNull
        private CategoryEntity category;
    }
}
