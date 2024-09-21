package org.restify.collection_service.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.restify.collection_service.Entity.Collection;
import org.restify.collection_service.Entity.Request;
import org.restify.collection_service.EntityRepository.CollectionRepository;
import org.restify.collection_service.EntityRepository.RequestRepository;
import org.restify.collection_service.Enum.EAuthType;
import org.restify.collection_service.Enum.EMethod;
import org.restify.collection_service.Exception.CollectionNotFoundException;
import org.restify.collection_service.Exception.RequestNotFoundException;
import org.restify.collection_service.FeignClient.FileClient;
import org.restify.collection_service.IService.ICollectionService;
import org.restify.collection_service.Payload.Mapper.CollectionMapper;
import org.restify.collection_service.Payload.Mapper.RequestMapper;
import org.restify.collection_service.Payload.Request.CollectionRequest;
import org.restify.collection_service.Payload.Request.FileRequest;
import org.restify.collection_service.Payload.Request.RequestRequest;
import org.restify.collection_service.Payload.Response.CollectionResponse;
import org.restify.collection_service.Payload.Response.FileResponse;
import org.restify.collection_service.Payload.Response.RequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionService implements ICollectionService{

    private final CollectionRepository collectionRepository;
    private final RequestRepository requestRepository;
    private final CollectionMapper collectionMapper;
    private final RequestMapper requestMapper;
    private final FileClient fileClient;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void createCollection(CollectionRequest collectionRequest) {
       Collection collection = collectionMapper.toCollection(collectionRequest);
       collectionRepository.save(collection); 
    }
    
    @Override
    @Transactional
    public void updateCollection(String collectionId, CollectionRequest collectionRequest) {
        Collection collection = getCollection(collectionId);
        collection.setName(collectionRequest.name());
        collection.setDescription(collectionRequest.description());
        collectionRepository.save(collection);
    }
    
    @Override
    public CollectionResponse getCollectionById(String collectionId) {
        return collectionMapper.fromCollection(getCollection(collectionId));
    }
    
    @Override
    public List<CollectionResponse> getAllCollections() {
        return collectionRepository.findAll().stream().map(
            collectionMapper::fromCollection
        ).collect(Collectors.toList());
    }
    @Override
    public Page<CollectionResponse> getAllCollections(String filter , Pageable pageable){
        Page<Collection> collectionsPage;
        
        if(filter != null && !filter.isEmpty())
        {
            collectionsPage = collectionRepository.findByNameContainingOrDescriptionContaining(filter, filter, pageable);
        }else{
            collectionsPage = collectionRepository.findAll(pageable);
        }
        return collectionsPage.map(collectionMapper::fromCollection);
    }
    @Override
    @Transactional
    public void deleteCollection(String collectionId) {
        if(collectionRepository.existsById(UUID.fromString(collectionId))){
            collectionRepository.deleteById(UUID.fromString(collectionId));
        }else {
            throw new CollectionNotFoundException("Cannot find collection with id : " + collectionId);
        }
    }
    
    @Override
    @Transactional
    public void addRequestToCollection(String collectionId, RequestRequest requestRequest) {
        Collection collection = getCollection(collectionId);
        Request request = requestMapper.toRequest(requestRequest);
        collection.getRequests().add(request);
        collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public void updateRequest(String requestId, RequestRequest requestRequest) {
        Request request = getRequest(requestId);
        request.setMethod(EMethod.valueOf(requestRequest.method()));
        request.setBody(requestRequest.body());
        request.setHeaders(requestRequest.headers());
        request.setAuthType(EAuthType.valueOf(requestRequest.authType()));
        request.setAuthToken(requestRequest.authToken());
        
        requestRepository.save(request);
    }

    @Override
    public RequestResponse getRequestById(String requestId) {
        return requestMapper.fromRequest(getRequest(requestId));
    }
    @Override
    public List<RequestResponse> getAllRequestsForCollection(String collectionId) {
        return requestRepository.findByCollectionId(UUID.fromString(collectionId)).stream().map(
            requestMapper::fromRequest
        ).collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void deleteRequest(String requestId) {
        Request request = getRequest(requestId);
        Collection collection = request.getCollection();
        collection.getRequests().remove(request);
        collectionRepository.save(collection);
        requestRepository.delete(request);
    }
    
    @Override
    @Transactional
    public void cloneCollection(String collectionId) {
        Collection collection = getCollection(collectionId);
        
        Collection newCollection = Collection.builder()
            .name(collection.getName() + "Clone")
            .description(collection.getDescription())
            .requests(collection.getRequests())
            .build();
        collectionRepository.save(newCollection);
    }

    @Override
    @Transactional
    public void cloneRequest(String requestId) {
        Request request = getRequest(requestId);
        Request newRequest = Request.builder()
            .method(request.getMethod())
            .url(request.getUrl())
            .body(request.getBody())
            .headers(request.getHeaders())
            .collection(request.getCollection())
            .authType(request.getAuthType())
            .authToken(request.getAuthToken())
            .build();
        requestRepository.save(newRequest);
    }

    @Override
    public String exportCollection(String collectionId) {
        Collection collection = getCollection(collectionId);
        try {
            String collectionJson = objectMapper.writeValueAsString(collection);

            FileRequest fileRequest = new FileRequest(
                "collection-" + collectionId + ".json",
                "application/json",
                collectionJson.getBytes().length,
                collectionJson.getBytes()
                );
            Optional<String> fileId = fileClient.uploadFile(fileRequest);

            return fileId
                .orElseThrow(() -> new RuntimeException("Failed to upload the collection file"));
        } catch (Exception e) {
            throw new RuntimeException("Error while exporting environment to JSON", e);
        }
    }
    
    @Override
    @Transactional
    public void importCollection(String fileId) {
        Optional<FileResponse> fileResponse = fileClient.downloadFile(fileId);
        FileResponse file = fileResponse
            .orElseThrow(() -> new RuntimeException("Failed to download the file with id: " + fileId));
        try {
            if (!file.contentType().equals("application/json")) {
                throw new RuntimeException("Invalid file format: Expected JSON");
            }
            String collectionJson = new String(file.file());
            CollectionRequest collectionRequest = objectMapper.readValue(collectionJson,CollectionRequest.class);
            Collection collection = collectionMapper.toCollection(collectionRequest);
            collectionRepository.save(collection);
        } catch (Exception e) {
            throw new RuntimeException("Error while importing environment", e);
        }
    }

    @Override
    public String exportRequest(String requestId) {
        Request request = getRequest(requestId);
        try {
            String requestJson = objectMapper.writeValueAsString(request);
            FileRequest fileRequest = new FileRequest(
                "request-" + requestId + ".json",
                "application/json",
                requestJson.getBytes().length,
                requestJson.getBytes()
                );
            Optional<String> fileId = fileClient.uploadFile(fileRequest);
            return fileId
                .orElseThrow(() -> new RuntimeException("Failed to upload the environment file"));
        } catch (Exception e) {
            throw new RuntimeException("Error while exporting environment to JSON", e);
        }
    }

    @Override
    @Transactional
    public void importRequest(String fileId) {
        Optional<FileResponse> fileResponse = fileClient.downloadFile(fileId);
        FileResponse file = fileResponse
                .orElseThrow(() -> new RuntimeException("Failed to download the file with id: " + fileId));
        try {
            if (!file.contentType().equals("application/json")) {
                throw new RuntimeException("Invalid file format: Expected JSON");
            }
            String requestJson = new String(file.file());
            RequestRequest requestRequest = objectMapper.readValue(requestJson, RequestRequest.class);
            Request request = requestMapper.toRequest(requestRequest);
            requestRepository.save(request);
        } catch (Exception e) {
            throw new RuntimeException("Error while importing environment", e);
        }
    }

    private Collection getCollection(String id)
    {
        return collectionRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new CollectionNotFoundException("Cannot find collections with id : " + id));
    }

    private Request getRequest(String id)
    {
        return requestRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new RequestNotFoundException("Cannot find request with id : " + id));
    }

}
