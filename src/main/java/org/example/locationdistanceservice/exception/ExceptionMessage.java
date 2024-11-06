package org.example.locationdistanceservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
    BINDING_RESULTS_ERROR("ошибка в данных запроса.");

    private final String message;
}
