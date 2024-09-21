package org.restify.collection_service.FeignClient;

import java.util.Optional;

import org.restify.collection_service.Payload.Request.FileRequest;
import org.restify.collection_service.Payload.Response.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "fileStorage-service", 
    url = "${application.config.file-url}", 
    dismiss404 = true
    )
public interface FileClient {
    @PostMapping
    Optional<String> uploadFile(@RequestBody FileRequest fileRequest);

    @DeleteMapping("/{fileId}")
    Optional<String> deleteFile(@PathVariable("fileId") String fileId);

    @GetMapping("/{fileId}")
    Optional<FileResponse> downloadFile(@PathVariable("fileId") String fileId);
}
