package io.jpractice.spring.sample.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: yanzhuzhu
 * Date: 13/04/2018
 */
@RestController
public class HealthController {
    @GetMapping("/check_health")
    public String ok(){
        return "ok";
    }
}
