package org.restify.fileStorage_service.IService;

import org.restify.fileStorage_service.Payload.Request.FileRequest;
import org.restify.fileStorage_service.Payload.Response.FileResponse;

public interface IFileService {
    String storeFile(FileRequest fileRequest);
    FileResponse getFile(String fileId);
    void deleteFile(String fileId);
}
