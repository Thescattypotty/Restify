package org.restify.fileStorage_service.Payload.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FileRequest(
    @NotBlank(message = "File name cannot be blank")
    String fileName,

    @NotBlank(message = "Content type cannot be blank")
    String contentType,
    
    @Min(value = 1, message = "File size must be greater than 0")
    long size,
    
    @NotNull(message = "File data cannot be null")
    @Size(min = 1, message = "File data must not be empty")
    byte[] file
) {
    
}
