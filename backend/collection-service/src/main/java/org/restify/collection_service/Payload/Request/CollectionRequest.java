package org.restify.collection_service.Payload.Request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CollectionRequest(
    @NotBlank(message = "Collection name cannot be blank")
    @Size(min = 2, max = 255, message = "Collection name must be between 2 and 255 characters")
    String name,

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    String environmentId,

    @Valid
    List<RequestRequest> requests
) {
}
