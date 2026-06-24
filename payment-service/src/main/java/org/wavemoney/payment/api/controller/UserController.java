package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.request.LoginRequest;
import org.wavemoney.payment.api.dto.request.PinUpdateRequest;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.request.UserUpdateRequest;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.LoginResponse;
import org.wavemoney.payment.api.dto.response.UserResponse;
import org.wavemoney.payment.api.service.UserService;

import java.util.List;
import java.util.Map;

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

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{phone}")
    public ResponseEntity<ApiResponse<UserResponse>> getByPhone(@PathVariable String phone) {
        UserResponse user = userService.getByPhone(phone);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{phone}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable String phone, @Valid @RequestBody UserUpdateRequest updReq) {
        UserResponse updated = userService.update(phone, updReq);
        return ResponseEntity.ok(ApiResponse.success(updated, HttpStatus.OK.value(), "User updated"));
    }

    @PutMapping("/change-pin")
    public ResponseEntity<ApiResponse<String>> changePin(@Valid @RequestBody PinUpdateRequest pinUpdateRequest) {
        userService.changePin(pinUpdateRequest);
        return ResponseEntity.ok(ApiResponse.success("Pin changed", HttpStatus.OK.value(), "Pin changed"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request.phone(), request.pin());
        return ResponseEntity.ok(ApiResponse.success(loginResponse, HttpStatus.OK.value(), "Login successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        userService.logout(phone);
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully", HttpStatus.OK.value(), "Token invalidated"));
    }

    @DeleteMapping("/{phone}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String phone) {
        userService.delete(phone);
        return ResponseEntity.ok(ApiResponse.success("User deleted", HttpStatus.OK.value(), "User deleted"));
    }


}
