package com.samsung.rest.controller;

import com.samsung.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @GetMapping(path = "/information/user")
    public String getUserInformation() throws IOException {
        return informationService.getUserInformation();
    }

}
