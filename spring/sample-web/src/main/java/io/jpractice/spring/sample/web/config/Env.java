package io.jpractice.spring.sample.web.config;

import org.springframework.util.StringUtils;

/**
 * Author: yanzhuzhu
 * Date: 13/04/2018
 */
public class Env {
    public static final String APP_ENV = "APP_ENV";

    public static boolean isDevelopEnv() {
        String env = System.getenv(APP_ENV);
        if (StringUtils.isEmpty(env)) {
            env = System.getProperty(APP_ENV);
        }
        return StringUtils.isEmpty(env);
    }
}
