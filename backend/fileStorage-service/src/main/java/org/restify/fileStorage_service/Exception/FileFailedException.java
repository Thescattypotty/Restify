package org.restify.fileStorage_service.Exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileFailedException extends RuntimeException
{
    private final String message;
}
