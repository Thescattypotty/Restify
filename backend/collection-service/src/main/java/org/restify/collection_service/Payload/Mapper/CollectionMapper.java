package org.restify.collection_service.Payload.Mapper;


import java.util.stream.Collectors;

import org.restify.collection_service.Entity.Collection;
import org.restify.collection_service.Payload.Request.CollectionRequest;
import org.restify.collection_service.Payload.Response.CollectionResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionMapper {
    
    private final RequestMapper requestMapper;

    public Collection toCollection(CollectionRequest collectionRequest)
    {
        return Collection.builder()
            .name(collectionRequest.name())
            .description(collectionRequest.description())
            .requests(collectionRequest.requests().stream().map(
                requestMapper::toRequest
            ).collect(Collectors.toList()))
            .environmentId(collectionRequest.environmentId())
            .build();
    }
    public CollectionResponse fromCollection(Collection collection)
    {
        return new CollectionResponse(
            collection.getId().toString(),
            collection.getName(),
            collection.getDescription(),
            collection.getEnvironmentId(),
            collection.getRequests().stream().map(requestMapper::fromRequest).collect(Collectors.toList()),
            collection.getCreatedAt(),
            collection.getUpdatedAt()
        );
    }
}
