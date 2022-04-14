package com.example.oauth2_asadullaev_behzod_b1.controller;

import com.example.oauth2_asadullaev_behzod_b1.dto.UserRegisterDto;
import com.example.oauth2_asadullaev_behzod_b1.dto.request.AuthenticationRequestDto;
import com.example.oauth2_asadullaev_behzod_b1.dto.response.AuthenticationResponseDto;
import com.example.oauth2_asadullaev_behzod_b1.dto.response.BaseApiResponse;
import com.example.oauth2_asadullaev_behzod_b1.entity.UserEntity;
import com.example.oauth2_asadullaev_behzod_b1.service.JwtProvider;
import com.example.oauth2_asadullaev_behzod_b1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserService userService;


    @ResponseBody
    @PostMapping("/user/login")
    public ResponseEntity<?> getToken(@ModelAttribute AuthenticationRequestDto authenticationRequestDto) {
        UserEntity userEntity =  userService.login(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
        if(userEntity == null)
            return ResponseEntity.ok().body(new BaseApiResponse(0, "username or password is incorrect", null));


        String accessToken = jwtProvider.generateAccessToken(userEntity);

        return ResponseEntity.ok().body(new AuthenticationResponseDto(1, accessToken));
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/cabinet")
    public String cabinet(){
        return "cabinet";
    }

    @ResponseBody
    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody UserRegisterDto userRegisterDto) {

        Boolean add = userService.add(userRegisterDto);

        if(add)
        return ResponseEntity.ok().body(new AuthenticationResponseDto(1, "added"));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthenticationResponseDto(1, "not saved"));

    }
}
