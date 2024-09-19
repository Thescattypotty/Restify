package org.restify.fileStorage_service.Payload.Response;

public record FileResponse(
    String fileId,
    String fileName,
    String contentType,
    long size,
    byte[] file,
    String uploadedDate
) {
    
}
