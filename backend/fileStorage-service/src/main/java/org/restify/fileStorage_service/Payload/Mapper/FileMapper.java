package org.restify.fileStorage_service.Payload.Mapper;

import org.restify.fileStorage_service.Entity.FileMetadata;
import org.restify.fileStorage_service.Payload.Response.FileResponse;
import org.springframework.stereotype.Service;

@Service
public class FileMapper {
    public FileResponse fromFile(FileMetadata fileMetadata, byte[] file)
    {
        return new FileResponse(
            fileMetadata.getId(),
            fileMetadata.getFileName(),
            fileMetadata.getContentType(),
            fileMetadata.getSize(),
            file,
            fileMetadata.getUploadedDate()
        );
    }
}
