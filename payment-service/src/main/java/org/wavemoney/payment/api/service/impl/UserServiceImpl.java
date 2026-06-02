package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;
import org.wavemoney.payment.api.entity.User;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.repository.UserRepository;
import org.wavemoney.payment.api.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw BusinessLogicException.business("USER_EMAIL_TAKEN", "Email is already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User " + id + " not found"));
        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
