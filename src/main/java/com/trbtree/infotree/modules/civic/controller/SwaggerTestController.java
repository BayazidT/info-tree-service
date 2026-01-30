package com.trbtree.infotree.modules.civic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-test")
public class SwaggerTestController {

    @GetMapping
    public String ok() {
        return "OK";
    }
}

