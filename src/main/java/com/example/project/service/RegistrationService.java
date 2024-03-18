package com.example.project.service;

import com.example.project.auth.AppUserService;
import com.example.project.model.RegistrationRequest;
import com.example.project.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;


    public String register(RegistrationRequest request){
        User.Gender gender = User.Gender.MALE;
        if ( request.getGender().toUpperCase(Locale.ROOT).equals("MALE")){
            gender = User.Gender.MALE;
        }
        else if ( request.getGender().toUpperCase(Locale.ROOT).equals("FEMALE")){
            gender = User.Gender.FEMALE;
        }
        else{
            throw new IllegalStateException("GENDER SHOULD BE ONE OF MALE/FEMALE. There are only two genders!!");
        }


        return appUserService.signupUser( new User(
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                request.getPassword(),
                request.getEmail(),
                UserRole.USER,
                gender
        ));


    }



}