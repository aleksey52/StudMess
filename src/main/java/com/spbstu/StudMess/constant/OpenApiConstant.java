package com.spbstu.StudMess.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiConstant {
    public static final String API_DESCRIPTION = "StudMess application";
    public static final String API_TITLE = "Application API";

    public static final String BEARER_SECURITY_SCHEME = "bearerAuthScheme";
    public static final String BEARER_SCHEME_NAME = "bearer";
    public static final String BEARER_FORMAT = "JWT";

    public static final String DESCRIPTION_DOC = "StudMess repository";
    public static final String URL_DOC = "https://github.com/aleksey52/StudMess";

    public static final String URL_LOCAL_SERVER = "http://localhost:8080";
    public static final String DESCRIPTION_LOCAL_SERVER = "Local service";

    public static final String CONTACT_NAME = "API Support";
    public static final String CONTACT_EMAIL = "aleksey52arzamas@gmail.com";
}
