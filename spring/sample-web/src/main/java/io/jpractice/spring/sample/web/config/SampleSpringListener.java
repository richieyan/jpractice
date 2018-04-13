package io.jpractice.spring.sample.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.security.Security;

/**
 * Author: yanzhuzhu
 * Date: 13/04/2018
 */
public class SampleSpringListener implements ApplicationListener<ApplicationStartingEvent>, Ordered {
    public static final String LOGBACK_DEV = "classpath:logback-dev.xml";

    private volatile boolean started = false;

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        if (!started) {
            started = true;
            if (Env.isDevelopEnv()) { //本地环境，即无ZAE_ENV变量的情况下
                Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                try {
                    ResourceUtils.getURL(LOGBACK_DEV);
                    System.setProperty("logging.config", LOGBACK_DEV);
                    rootLogger.warn("Running in >>>>>>[DEV]<<<<<");
                } catch (FileNotFoundException e) {
                    rootLogger.warn("Running in [DEV] but no logback-dev.xml found!");
                }
            }

            //有效的DNS缓存失效时间(s)
            Security.setProperty("networkaddress.cache.ttl", "60");
            //无效DNS解析缓存失效时间(s)
            Security.setProperty("networkaddress.cache.negative.ttl", "3");
        }
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
