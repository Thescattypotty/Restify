package org.restify.fileStorage_service.Payload.Response;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {
}
