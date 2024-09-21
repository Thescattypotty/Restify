package org.restify.collection_service.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestNotFoundException extends RuntimeException{
    private final String message;
}
