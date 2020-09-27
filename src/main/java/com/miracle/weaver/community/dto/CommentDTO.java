package com.miracle.weaver.community.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class CommentDTO {

    @Data
    static public class List {

        private int id;

        private String content;
    }

    @Data
    static public class Create {

        @NotEmpty
        @NotNull
        private String content;
    }

    @Data
    static public class Detail {

        private int id;
        private String content;
    }
}
