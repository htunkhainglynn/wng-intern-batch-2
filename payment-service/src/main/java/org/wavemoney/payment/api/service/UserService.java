package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;

public interface UserService {

    UserResponse create(UserRequest request);

    UserResponse getById(String id);
}
