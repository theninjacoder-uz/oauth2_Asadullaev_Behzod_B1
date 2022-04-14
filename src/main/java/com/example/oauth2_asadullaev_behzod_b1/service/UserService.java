package com.example.oauth2_asadullaev_behzod_b1.service;

import com.example.oauth2_asadullaev_behzod_b1.dto.UserRegisterDto;
import com.example.oauth2_asadullaev_behzod_b1.entity.CustomOauth2User;
import com.example.oauth2_asadullaev_behzod_b1.entity.UserEntity;
import com.example.oauth2_asadullaev_behzod_b1.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserEntity login(String username, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()
                && passwordEncoder.matches(password, optionalUser.get().getPassword()))
            return optionalUser.get();

        return null;
    }


    public String authenticate(DefaultOidcUser customOauth2User) {

        Optional<UserEntity> optionalUser =
                userRepository.findByUsername(customOauth2User.getAttributes().get("email").toString());

        if (optionalUser.isEmpty() && (Boolean) customOauth2User.getAttributes().get("email_verified")) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(customOauth2User
                    .getAttributes().get("name").toString());
            userEntity.setPermission("USER");
            userEntity.setUsername(customOauth2User.getAttributes().get("email").toString());
            userRepository.save(userEntity);

            return jwtProvider.generateAccessToken(userEntity);
        }
        else if(optionalUser.isPresent() && (Boolean) customOauth2User.getAttributes().get("email_verified"))
        {
            return jwtProvider.generateAccessToken(optionalUser.get());

        }
            return null;
    }

    public Boolean add(UserRegisterDto userRegisterDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userEntity.setPermission(userRegisterDto.getPermission().get(0));
        userEntity.setName(userRegisterDto.getName());
        userEntity.setUsername(userRegisterDto.getUsername());
        userRepository.save(userEntity);
        return true;
    }

}
