package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.request.UserUpdateRequest;
import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.User;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.repository.UserRepository;
import org.wavemoney.payment.api.service.UserService;
import org.wavemoney.payment.api.service.WalletService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByPhoneOrNrc(request.phone(), request.nrc())) {
            throw BusinessLogicException.business("ACCOUNT_TAKEN", "Account is already taken");
        }

        User user = User.builder()
                .name(request.name())
                .phone(request.phone())
                .nrc(request.nrc())
                .password(request.password())
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        walletService.create(WalletRequest.builder().phoneNumber(saved.getPhone()).build());
        return toResponse(saved);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return toResponse(users);
    }

    @Override
    public UserResponse getByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User with phone " + phone + " not found"));
        return toResponse(user);
    }

    @Override
    public UserResponse login(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessLogicException.auth("INVALID_CREDENTIALS", "Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw BusinessLogicException.auth("INVALID_CREDENTIALS", "Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return toResponse(user);
    }

    @Override
    public void logout(String id) {
        // stateless/logout stub - update lastLogin to null or leave as-is. We'll update nothing for now.
        // Could be extended to manage tokens in Redis.
        if (!userRepository.existsById(id)) {
            throw BusinessLogicException.notFound("USER_NOT_FOUND", "User " + id + " not found");
        }
    }

    @Override
    public UserResponse update(String phone, UserUpdateRequest updReq) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User with phone number /' " + phone + " /' not found"));

        user.setName(updReq.name());
        //user.setPassword(updReq.password());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User " + id + " not found"));

        if (!user.getPassword().equals(oldPassword)) {
            throw BusinessLogicException.business("INVALID_PASSWORD", "Old password does not match");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void delete(String phone) {
        if (!userRepository.existsByPhone(phone)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteByPhone(phone);
        walletService.deleteWalletByPhone(phone);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getPhone(), user.getPassword(), user.getNrc(), user.getLevel());
    }

    private List<UserResponse> toResponse(List<User> users) {
        return users
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
