package com.example.project.controller;

import com.example.project.model.RegistrationRequest;
import com.example.project.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request ){
        return  new ResponseEntity<String>(registrationService.register(request), HttpStatus.OK);
    }

}