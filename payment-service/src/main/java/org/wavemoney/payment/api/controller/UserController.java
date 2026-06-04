package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.request.LoginRequest;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.UserResponse;
import org.wavemoney.payment.api.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserRequest request) {
        UserResponse created = userService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, HttpStatus.CREATED.value(), "User created"));
    }

    @GetMapping("/{phone}")
    public ResponseEntity<ApiResponse<UserResponse>> getByPhone(@PathVariable String phone) {
        UserResponse user = userService.getByPhone(phone);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{phone}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable String  phone, @Valid @RequestBody UserRequest request) {
        UserResponse updated = userService.update(request);
        return ResponseEntity.ok(ApiResponse.success(updated, HttpStatus.OK.value(), "User updated"));
    }

    @PutMapping("/{phone}/password")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable String phone,
                                                              @RequestBody String oldPassword,
                                                              @RequestBody String newPassword) {
        userService.changePassword(phone, oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Password changed", HttpStatus.OK.value(), "Password changed"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest request) {
        UserResponse user = userService.login(request.phone(), request.password());
        return ResponseEntity.ok(ApiResponse.success(user, HttpStatus.OK.value(), "Login successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@Valid @RequestBody String phone) {
        userService.logout(phone);
        return ResponseEntity.ok(ApiResponse.success("Logged out", HttpStatus.OK.value(), "Logged out"));
    }

    @DeleteMapping("/{phone}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String phone) {
        userService.delete(phone);
        return ResponseEntity.ok(ApiResponse.success("User deleted", HttpStatus.OK.value(), "User deleted"));
    }


}
