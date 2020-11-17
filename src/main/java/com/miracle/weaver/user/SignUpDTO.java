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
        private String password;

        @NotNull
        @NotEmpty
        private String nickname;

        @NotNull
        @NotEmpty
        private String personality;
    }

    @Data
    public static class PatchRequest {

        @NotNull
        @NotEmpty
        private String username;

        private String nickname;

        private String personality;
    }

    @Data
    @Builder
    public static class Response {

        private String username;
        private String personality;
    }
}
