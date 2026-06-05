package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.PinUpdateRequest;
import org.wavemoney.payment.api.dto.request.UserRequest;
import org.wavemoney.payment.api.dto.request.UserUpdateRequest;
import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.UserResponse;
import org.wavemoney.payment.api.entity.User;
import org.wavemoney.payment.api.enums.WalletStatus;
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
                .pin(request.pin())
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        walletService.create(WalletRequest.builder().phone(saved.getPhone()).build());
        return toResponse(saved, WalletStatus.ACTIVE.name());
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

        // wallet status
        String walletStatus = walletService.getWalletStatusByPhone(phone);
        return toResponse(user, walletStatus);
    }

    @Override
    public UserResponse login(String phone, String pin) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessLogicException.auth("INVALID_CREDENTIALS", "Invalid credentials"));

        if (!user.getPin().equals(pin)) {
            throw BusinessLogicException.auth("INVALID_CREDENTIALS", "Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String walletStatus = walletService.getWalletStatusByPhone(phone);
        return toResponse(user, walletStatus);
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
        User saved = userRepository.save(user);
        String walletStatus = walletService.getWalletStatusByPhone(phone);
        return toResponse(saved, walletStatus);
    }

    @Override
    public void changePin(PinUpdateRequest pinUpdateRequest) {
        User user = userRepository.findByPhone(pinUpdateRequest.phone())
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User " + pinUpdateRequest.phone() + " not found"));
        String oldPin = pinUpdateRequest.oldPin();
        String newPin = pinUpdateRequest.newPin();
        if (!user.getPin().equals(oldPin)) {
            throw BusinessLogicException.business("INVALID_PIN", "Old pin does not match");
        }

        user.setPin(newPin);
        userRepository.save(user);
    }

    @Override
    public void delete(String phone) {
        if (!userRepository.existsByPhone(phone)) {
            throw BusinessLogicException.business("USER_NOT_FOUND", "User with phone " + phone + " not found");
        }

        userRepository.deleteByPhone(phone);
        walletService.deleteWalletByPhone(phone);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder().name(user.getName())
                .phone(user.getPhone())
                .level(user.getLevel()).build();
    }

    private UserResponse toResponse(User user, String walletStatus) {
        return UserResponse.builder().name(user.getName())
                .phone(user.getPhone())
                .walletStatus(walletStatus)
                .level(user.getLevel()).build();
    }

    private List<UserResponse> toResponse(List<User> users) {
        return users
                .stream()
                .map(u -> toResponse(u, walletService.getWalletStatusByPhone(u.getPhone())))
                .collect(Collectors.toList());
    }
}
