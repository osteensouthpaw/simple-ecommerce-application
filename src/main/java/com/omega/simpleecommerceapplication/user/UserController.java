package com.omega.simpleecommerceapplication.user;

import com.omega.simpleecommerceapplication.user.registration.UserRegistrationRequest;
import com.omega.simpleecommerceapplication.user.registration.UserRegistrationService;
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
