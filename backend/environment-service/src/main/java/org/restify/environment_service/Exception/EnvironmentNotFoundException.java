package org.restify.environment_service.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnvironmentNotFoundException extends RuntimeException {
    private final String message;
}
