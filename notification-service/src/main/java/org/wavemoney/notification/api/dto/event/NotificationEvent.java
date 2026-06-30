package org.wavemoney.notification.api.dto.event;

import lombok.Builder;

@Builder
public record NotificationEvent(
        String phone,
        String message,
        String type,
        String status
) {}