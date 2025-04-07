package com.pajiniweb.oriachad_backend.security.domain.constans;

public abstract class ConstansStrings {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "secretkey";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String[] BYPASS_AND_POINT_LIST={

           /* >>>>>> ORIACHAD <<<<<<*/
            "/api/v1/audiences/**",

            "/api/v1/audience-tasks/**",

            "/api/v1/communes/**",

            "/api/v1/dairas/**",

            "/api/v1/difficulties/**",

            "/api/v1/difficulty-solutions/**",

            "/api/v1/establishments/**",

            "/api/v1/FTP/**",

            "/api/v1/general-objectif-tasks/**",

            "/api/v1/general-objectives/**",

            "/api/v1/human-tools/**",

            "/api/v1/human-tools-tasks/**",

            "/api/v1/official-txts/**",

            "/api/v1/operat-general-objectifs/**",

            "/api/v1/operat-objectifs/**",

            "/api/v1/scolarYearTasks/**",

            "/api/v1/solutions/**",

            "/api/v1/taskCategories/**",

            "/api/v1/task-difficulties/**",

            "/api/v1/task-official-txts/**",

            "/api/v1/TcTask/**",

            "/api/v1/users/**",

            "/api/v1/authenticate/**",

            "/api/v1/wilayas/**",

            "/api/v1/years/**",

            "/api/v1/scripts/**",

            "/api/v1/forget-password/**",

            "/api/v1/operat-objectif-tasks/**",

            "/api/v1/plans/**",

            "/api/v1/plan-users/**",

            "/api/v1/pop-ups",

            "/api/v1/official-txts-category"
    };
}
