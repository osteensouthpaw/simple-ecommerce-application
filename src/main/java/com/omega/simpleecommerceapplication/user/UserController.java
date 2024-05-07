package com.omega.simpleecommerceapplication.user;

import com.omega.simpleecommerceapplication.user.registration.UserRegistrationService;
import com.omega.simpleecommerceapplication.commons.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRegistrationService registrationService;;
    private final AppUserService appUserService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findUserById(@PathVariable Integer userId) {
        return appUserService.findById(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
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


    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable Integer userId,
                           @RequestBody UserUpdateRequest request) {
        appUserService.updateUser(userId, request);
    }


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Integer userId) {
        appUserService.deleteUserById(userId);
    }
}
