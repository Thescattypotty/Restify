package org.restify.fileStorage_service.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileNotFoundException extends RuntimeException
{
    private final String message;
}
