package org.restify.environment_service.IService;

import java.util.List;

import org.restify.environment_service.Payload.Request.EnvironmentRequest;
import org.restify.environment_service.Payload.Response.EnvironmentResponse;

public interface IEnvironmentService {
    void createAnEnvironment(EnvironmentRequest environmentRequest);
    void addOrUpdateVariable(String environmentId, String key, String value);
    EnvironmentResponse getSpecificEnvironment(String environmentId);
    List<EnvironmentResponse> getAllEnvironment();
    void deleteAnEnvironment(String environmentId);    
    void cloneAnEnvironment(String environmentId);

    String exportEnvironment(String environmentId);

    void importEnvironment(String fileId);
    
    void deleteVariable(String environmentId, String key);
    boolean environmentExists(String environmentId);

}
