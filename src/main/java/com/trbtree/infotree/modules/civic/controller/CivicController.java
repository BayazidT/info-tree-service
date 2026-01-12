package com.trbtree.infotree.modules.civic.controller;


import com.trbtree.infotree.modules.civic.sevice.CivicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info-tree-service/api/v1/private/civic")
public class CivicController {
    // here all the emergency information will be served...
    private final CivicService civicService;

    @PostMapping("/")
    public String createCivicInfo(){
        return civicService.createCivicInfo();
    }
}
