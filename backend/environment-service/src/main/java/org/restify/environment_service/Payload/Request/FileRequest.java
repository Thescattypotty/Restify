package org.restify.environment_service.Payload.Request;

public record FileRequest(
    String fileName,
    String contentType,
    long size,
    byte[] file
) {
    
}
