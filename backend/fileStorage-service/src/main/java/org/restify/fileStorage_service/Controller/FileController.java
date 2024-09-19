package org.restify.fileStorage_service.Controller;

import org.restify.fileStorage_service.Payload.Request.FileRequest;
import org.restify.fileStorage_service.Payload.Response.FileResponse;
import org.restify.fileStorage_service.Service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestBody @Valid FileRequest fileRequest)
    {
        return ResponseEntity.ok(fileService.storeFile(fileRequest));
    }
    
    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponse> downloadFile(@PathVariable("fileId") String fileId) {
        return ResponseEntity.ok(fileService.getFile(fileId));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }

}
