package com.omega.simpleecommerceapplication.user;

import com.omega.simpleecommerceapplication.exceptions.DuplicateResourceException;
import com.omega.simpleecommerceapplication.exceptions.NoUpdateDetectedException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import com.omega.simpleecommerceapplication.user.token.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final ConfirmationTokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userDtoMapper)
                .collect(Collectors.toList());
    }

    //returns user DTO
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
    }


    //returns the whole user entity
    public AppUser findByUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
    }


    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exists with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public AppUser createUser(AppUser user) {
        boolean userExists = existsByEmail(user.getEmail());
        if (userExists) {
            AppUser savedUser = findByEmail(user.getEmail());
            if (!savedUser.isEnabled()) {
                tokenService.sendVerificationEmail(savedUser);
                return savedUser;
            }
            throw new DuplicateResourceException("email already taken");
        }

        return userRepository.save(user);
    }

    public void updateUser(Integer id, UserUpdateRequest updateRequest) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        boolean changes = false;

        if (updateRequest.firstName() != null && !updateRequest.firstName().equals(user.getFirstName())) {
            user.setFirstName(updateRequest.firstName());
            changes = true;
        }

        if (updateRequest.lastName() != null && !updateRequest.lastName().equals(user.getLastName())) {
            user.setLastName(updateRequest.lastName());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(user.getEmail())) {
            if (existsByEmail(updateRequest.email()))
                throw new DuplicateResourceException("email already exist");

            user.setEmail(updateRequest.email());
            changes = true;
        }

        //update age
        if (updateRequest.password() != null && updateRequest.password().equals(user.getPassword())) {
            user.setPassword(updateRequest.password());
            changes = true;
        }

        if (!changes)
            throw new NoUpdateDetectedException("No changes detected");

        userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")));
    }
}
