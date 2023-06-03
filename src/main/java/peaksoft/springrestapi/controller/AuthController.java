package peaksoft.springrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.springrestapi.Service.UserService;
import peaksoft.springrestapi.dto.*;
import peaksoft.springrestapi.model.User;
import peaksoft.springrestapi.repository.UserRepo;
import peaksoft.springrestapi.security.jwt.JwtTokenFilter;
import peaksoft.springrestapi.security.jwt.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Tag(name = "Auth Controller", description = "Registration")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepo userRepo;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;
    @PostMapping("sign-up")
    @Operation(summary = "signing up", description = "re entering site")
    public UserResponse signUp(@RequestBody UserRequest userRequest) {
        return userService.createStudent(userRequest);
    }
    @PostMapping("sign-in")
    @Operation(summary = "signing in", description = "entering site first time")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        User user = userRepo.findByUsername(token.getName()).get();
        return loginMapper.loginView(jwtTokenUtil.generateToken(user), "Successful", user);
    }
}
