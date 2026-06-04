package org.wavemoney.payment.api.service;

import jakarta.validation.Valid;
import org.wavemoney.payment.api.dto.request.PinUpdateRequest;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.request.UserUpdateRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);
    UserResponse getByPhone (String phone);

    UserResponse login(String phone, String pin);

    void logout(String id);

    UserResponse update(String phone, UserUpdateRequest updReq);

    void delete(String id);

    List<UserResponse> getAllUsers();

    void changePin(PinUpdateRequest pinUpdateRequest);
}
