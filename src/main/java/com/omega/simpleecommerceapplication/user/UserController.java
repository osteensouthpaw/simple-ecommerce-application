package com.omega.simpleecommerceapplication.user;

import com.omega.simpleecommerceapplication.registration.UserRegistrationRequest;
import com.omega.simpleecommerceapplication.registration.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRegistrationService registrationService;;
    private final AppUserService appUserService;

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Integer userId) {
        return appUserService.findById(userId);
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return appUserService.getAllUsers();
    }

    @PostMapping("/new")
    public String createUser(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerUser(request);
    }


    @GetMapping("/confirm")
    public void getUserToken(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
    }


    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Integer userId,
                           @RequestBody UserUpdateRequest request) {
        appUserService.updateUser(userId, request);
    }


    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        appUserService.deleteUserById(userId);
    }
}
