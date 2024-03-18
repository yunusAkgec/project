package com.example.project.controller;


import com.example.project.auth.AppUserService;
import com.example.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "api/")
public class AuthController {


    private final AppUserService appUserService;

    @Autowired
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping(path = "auth")
    public HashMap<String, Object> isAuthenticated(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashMap<String ,Object >  hs = new HashMap<>();

        if ( auth!= null && auth.getPrincipal() != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken) ){

            hs.put("AUTHENTICATED",  true);
            User userDetails = (User) auth.getPrincipal();
            userDetails.setPassword("");
            hs.put("USER" , userDetails );
        }
        else{
            hs.put("AUTHENTICATED", false);
        }
        return  hs;
    }


}