package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable String id) {
        UserResponse user = userService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
