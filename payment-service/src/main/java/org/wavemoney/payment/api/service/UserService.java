package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;

public interface UserService {

    UserResponse create(UserRequest request);
    UserResponse getByPhone (String phone);

    UserResponse login(String phone, String password);

    void logout(String id);

    UserResponse update(String id, UserRequest request);

    void changePassword(String id, String oldPassword, String newPassword);
    void delete(String id);
}
