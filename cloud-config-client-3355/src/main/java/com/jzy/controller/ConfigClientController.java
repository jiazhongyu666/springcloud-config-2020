package com.jzy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzy
 * @date 2020/6/24 19:13
 */
@RestController
public class ConfigClientController {

    @Value("${server.info}")
    String serverPort;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return serverPort;
    }
}
