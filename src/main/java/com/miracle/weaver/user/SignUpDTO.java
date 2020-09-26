package com.miracle.weaver.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class SignUpDTO {

    @Data
    public static class Request {

        @NotNull
        @NotEmpty
        private String username;

        @NotNull
        @NotEmpty
        private String password1;

        @NotNull
        @NotEmpty
        private String password2;

        private String personality;
    }

    @Data
    @Builder
    public static class Response {

        private String username;
        private String personality;
    }
}
