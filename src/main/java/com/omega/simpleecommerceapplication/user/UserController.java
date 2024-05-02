package com.omega.simpleecommerceapplication.user;

import com.omega.simpleecommerceapplication.user.registration.UserRegistrationService;
import com.omega.simpleecommerceapplication.user.token.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse<UserDto> findAllUsers(
                            @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "10") int size,
                            @RequestParam(required = false, defaultValue = "userId") String sortField,
                            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) {
        return appUserService.getAllUsers(page, size, sortField, sortDirection);
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
