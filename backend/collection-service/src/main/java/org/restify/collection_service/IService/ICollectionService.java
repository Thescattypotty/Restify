package org.restify.collection_service.IService;

import java.util.List;

import org.restify.collection_service.Payload.Request.CollectionRequest;
import org.restify.collection_service.Payload.Request.RequestRequest;
import org.restify.collection_service.Payload.Response.CollectionResponse;
import org.restify.collection_service.Payload.Response.RequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICollectionService {

    //Cruds For Collection
    void createCollection(CollectionRequest collectionRequest);
    void updateCollection(String collectionId, CollectionRequest collectionRequest);
    CollectionResponse getCollectionById(String collectionId);
    List<CollectionResponse> getAllCollections();
    Page<CollectionResponse> getAllCollections(String filter , Pageable pageable);

    void deleteCollection(String collectionId);

    // Manage Requests inside a Collection
    void addRequestToCollection(String collectionId, RequestRequest requestRequest);
    void updateRequest(String requestId, RequestRequest requestRequest);
    RequestResponse getRequestById(String requestId);
    List<RequestResponse> getAllRequestsForCollection(String collectionId);
    void deleteRequest(String requestId);

    // Additional features
    void cloneCollection(String collectionId);
    void cloneRequest(String requestId);

    // Export/Import functionalities for collections
    String exportCollection(String collectionId);
    void importCollection(String fileId);

    // Export/Import functionalities for requests
    String exportRequest(String requestId);
    void importRequest(String fileId);
}
