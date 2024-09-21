package org.restify.collection_service.Controller;

import java.util.List;

import org.restify.collection_service.Payload.Request.RequestRequest;
import org.restify.collection_service.Payload.Response.RequestResponse;
import org.restify.collection_service.Service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final CollectionService collectionService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> addRequestToCollection(@PathVariable("id") String collectionId , @RequestBody @Valid RequestRequest requestRequest){
        collectionService.addRequestToCollection(collectionId, requestRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRequest(@PathVariable("id") String requestId,
            @RequestBody @Valid RequestRequest requestRequest) {
        collectionService.updateRequest(requestId, requestRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<RequestResponse> getRequestById(@PathVariable("id") String requestId){
        return ResponseEntity.ok(collectionService.getRequestById(requestId));
    }
    @GetMapping("/collection/{id}")
    public ResponseEntity<List<RequestResponse>> getAllRequestsForCollection(@PathVariable("id") String collectionId){
        return ResponseEntity.ok(collectionService.getAllRequestsForCollection(collectionId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("id") String requestId){
        collectionService.deleteRequest(requestId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/clone/{id}")
    public ResponseEntity<Void> cloneRequest(@PathVariable("id") String id){
        collectionService.cloneRequest(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @GetMapping("/export/{id}")
    public ResponseEntity<String> exportRequest(@PathVariable("id") String id){
        return ResponseEntity.ok(collectionService.exportRequest(id));
    }
    @PostMapping("/import/{id}")
    public ResponseEntity<Void> importRequest(@PathVariable("id") String fileId){
        collectionService.importRequest(fileId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
