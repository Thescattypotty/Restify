package org.restify.collection_service.Controller;

import org.restify.collection_service.Payload.Request.CollectionRequest;
import org.restify.collection_service.Payload.Response.CollectionResponse;
import org.restify.collection_service.Service.CollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor
public class CollectionController {
    
    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<Void> createNewCollection(@RequestBody @Valid CollectionRequest collectionRequest){
        collectionService.createCollection(collectionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<CollectionResponse>> getAllCollections(
        @RequestParam(required = false) String filter,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ResponseEntity.ok(collectionService.getAllCollections(filter, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCollection(@PathVariable("id") String id , @RequestBody @Valid CollectionRequest collectionRequest){
        collectionService.updateCollection(id, collectionRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponse> getCollectionById(@PathVariable("id") String id){
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionById(@PathVariable("id") String id){
        collectionService.deleteCollection(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/clone/{id}")
    public ResponseEntity<Void> cloneCollection(@PathVariable("id") String id){
        collectionService.cloneCollection(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    
    @GetMapping("/export/{id}") // return json format of the collection
    public ResponseEntity<String> exportCollection(@PathVariable("id") String id) {
        return ResponseEntity.ok(collectionService.exportCollection(id));
    }

    @PostMapping("/import/{id}")
    public ResponseEntity<Void> importCollection(@PathVariable("id") String fileId)
    {
        collectionService.importCollection(fileId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    
    
}
