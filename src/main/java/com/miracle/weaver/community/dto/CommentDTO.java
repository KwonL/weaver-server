package com.miracle.weaver.community.dto;

import lombok.Data;

public class CommentDTO {

    @Data
    static public class List {

        private int id;

        private String content;
    }
}
