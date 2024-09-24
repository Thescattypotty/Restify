package org.restify.environment_service.IService;

import org.restify.environment_service.Payload.Request.EnvironmentRequest;
import org.restify.environment_service.Payload.Response.EnvironmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEnvironmentService {
    void createAnEnvironment(EnvironmentRequest environmentRequest);
    void addOrUpdateVariable(String environmentId, String key, String value);
    EnvironmentResponse getSpecificEnvironment(String environmentId);
    Page<EnvironmentResponse> getAllEnvironment(String filter , Pageable pageable);
    void deleteAnEnvironment(String environmentId);    
    void cloneAnEnvironment(String environmentId);

    String exportEnvironment(String environmentId);

    void importEnvironment(String fileId);
    
    void deleteVariable(String environmentId, String key);
    boolean environmentExists(String environmentId);

}
