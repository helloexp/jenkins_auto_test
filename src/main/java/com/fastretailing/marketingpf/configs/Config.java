package com.fastretailing.marketingpf.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Config {

    @Setter
    @Getter
    private String env;

    @Setter
    @Getter
    private String datasetGaUqjp;

    @Setter
    @Getter
    private String datasetGaGujp;

    @Setter
    @Getter
    private String datasetGaUqjpApp;

    @Setter
    @Getter
    private Aes aes;

    @Setter
    @Getter
    private Cors cors;

    @Setter
    @Getter
    private MkdbApi mkdbApi;

    public static class Aes {

        @Setter
        @Getter
        private String secretKey;
    }

    public static class Cors {

        @Setter
        @Getter
        private String[] allowedOrigins;

        @Setter
        @Getter
        private String[] allowedHeaders;
    }

    public static class MkdbApi {

        @Setter
        @Getter
        private String baseUrl;

        @Setter
        @Getter
        private String uriPrefix;
    }

    @Setter
    @Getter
    private Mkpf mkpf;

    public static class Mkpf {

        /**
         * Return front-end base URL
         */
        @Setter
        @Getter
        private String baseUrl;

        /**
         * Return back-end URL
         */
        @Setter
        @Getter
        private String apiUrl;

        /**
         * Get callback URL that receive token from DMP<br>
         * URI must be matched with corresponding end point
         */
        @Setter
        @Getter
        private String platformAuthCallbackUrl;

        /**
         * This URL handle front-end authorization success event<br>
         */
        @Setter
        @Getter
        private String frontendAuthCallbackUrl;

        @Setter
        @Getter
        private String uriPrefix;
    }


    @Setter
    @Getter
    private GoogleAds googleAds;

    public static class GoogleAds {

        /**
         * Default app-id that is set to GUJP Android user list
         */
        @Getter
        @Setter
        private String userListGUJPAndroidAppId;

        /**
         * Default app-id that is set to GUJP IOs user list
         */
        @Getter
        @Setter
        private String userListGUJPIOsAppId;

        /**
         * Default app-id that is set to UQJP Android user list
         */
        @Getter
        @Setter
        private String userListUQJPAndroidAppId;

        /**
         * Default app-id that is set to UQJP IOs user list
         */
        @Getter
        @Setter
        private String userListUQJPIOsAppId;
    }

    @Getter
    @Setter
    private String mkpfSchedulerIp;
}
