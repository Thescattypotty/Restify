package org.restify.environment_service.Payload.Mapper;

import org.restify.environment_service.Entity.Environment;
import org.restify.environment_service.Payload.Request.EnvironmentRequest;
import org.restify.environment_service.Payload.Response.EnvironmentResponse;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentMapper {
    
    public Environment toEnvironment(EnvironmentRequest environmentRequest)
    {
        return Environment.builder()
            .name(environmentRequest.name())
            .variables(environmentRequest.variables())
            .build();
    }

    public EnvironmentResponse fromEnvironment(Environment environment)
    {
        return new EnvironmentResponse(
            environment.getId().toString(),
            environment.getName(),
            environment.getVariables(),
            environment.getCreatedAt(),
            environment.getUpdatedAt()
        );
    }
}
