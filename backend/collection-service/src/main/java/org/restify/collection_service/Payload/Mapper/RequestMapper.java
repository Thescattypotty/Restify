package org.restify.collection_service.Payload.Mapper;

import org.restify.collection_service.Entity.Request;
import org.restify.collection_service.Enum.EAuthType;
import org.restify.collection_service.Enum.EMethod;
import org.restify.collection_service.Payload.Request.RequestRequest;
import org.restify.collection_service.Payload.Response.RequestResponse;
import org.springframework.stereotype.Service;

@Service
public class RequestMapper {

    public Request toRequest(RequestRequest requestRequest)
    {
        return Request.builder()
            .method(EMethod.valueOf(requestRequest.method()))
            .url(requestRequest.url())
            .body(requestRequest.body())
            .headers(requestRequest.headers())
            .collection(null)
            .authType(EAuthType.valueOf(requestRequest.authToken()))
            .authToken(requestRequest.authToken())
            .build();
    }

    public RequestResponse fromRequest(Request request)
    {
        return new RequestResponse(
            request.getId().toString(),
            request.getMethod(),
            request.getUrl(),
            request.getBody(),
            request.getHeaders(),
            request.getAuthType(),
            request.getAuthToken(),
            request.getCreatedAt(),
            request.getUpdatedAt()
        );
    }
}
