package org.restify.environment_service.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.restify.environment_service.Entity.Environment;
import org.restify.environment_service.EntityRepository.EnvironmentRepository;
import org.restify.environment_service.Exception.EnvironmentNotFoundException;
import org.restify.environment_service.FeignClient.FileClient;
import org.restify.environment_service.IService.IEnvironmentService;
import org.restify.environment_service.Payload.Mapper.EnvironmentMapper;
import org.restify.environment_service.Payload.Request.EnvironmentRequest;
import org.restify.environment_service.Payload.Request.FileRequest;
import org.restify.environment_service.Payload.Response.EnvironmentResponse;
import org.restify.environment_service.Payload.Response.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvironmentService implements IEnvironmentService
{
    private final EnvironmentRepository environmentRepository;
    private final EnvironmentMapper environmentMapper;
    private final ObjectMapper objectMapper;
    private final FileClient fileClient;

    @Override
    @Transactional
    public void createAnEnvironment(EnvironmentRequest environmentRequest) {
        Environment environment = environmentMapper.toEnvironment(environmentRequest);
        environmentRepository.save(environment);
    }

    @Override
    @Transactional
    public void addOrUpdateVariable(String environmentId, String key, String value) {
        Environment environment = getEnvironment(environmentId);
        environment.getVariables().putIfAbsent(key, value);
        environmentRepository.save(environment);
    }

    @Override
    public EnvironmentResponse getSpecificEnvironment(String environmentId) {
        Environment environment = getEnvironment(environmentId);
        return environmentMapper.fromEnvironment(environment);
    }

    @Override
    public List<EnvironmentResponse> getAllEnvironment() {
        return environmentRepository.findAll().stream().map(
            environmentMapper::fromEnvironment
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAnEnvironment(String environmentId) {
        if(environmentRepository.existsById(UUID.fromString(environmentId)))
        {
            environmentRepository.deleteById(UUID.fromString(environmentId));
        }else{
            throw new EnvironmentNotFoundException("Cannot find environment with id :" + environmentId);
        }

    }

    @Override
    @Transactional
    public void cloneAnEnvironment(String environmentId) {
        Environment environment = getEnvironment(environmentId);
        Environment newEnvironment = Environment.builder()
            .name(environment.getName())
            .variables(environment.getVariables())
            .build();
        environmentRepository.save(newEnvironment);
    }


    @Override
    public String exportEnvironment(String environmentId){
        Environment environment = getEnvironment(environmentId);
        try {
            String environmentJson = objectMapper.writeValueAsString(environment);

            FileRequest fileRequest = new FileRequest(
                "environment-" + environmentId + ".json",
                "application/json",
                environmentJson.getBytes().length,
                environmentJson.getBytes()
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
    public void importEnvironment(String fileId) {
        Optional<FileResponse> fileResponse = fileClient.downloadFile(fileId);

        FileResponse file = fileResponse.orElseThrow(() -> 
            new RuntimeException("Failed to download the file with id: " + fileId));

        try {
            if (!file.contentType().equals("application/json")) {
                throw new RuntimeException("Invalid file format: Expected JSON");
            }
            String environmentJson = new String(file.file());
            EnvironmentRequest environmentRequest = objectMapper.readValue(environmentJson, EnvironmentRequest.class);

            Environment environment = environmentMapper.toEnvironment(environmentRequest);
            environmentRepository.save(environment);

        } catch (Exception e) {
            throw new RuntimeException("Error while importing environment", e);
        }
    }

    @Override
    @Transactional
    public void deleteVariable(String environmentId, String key) {
        Environment environment = getEnvironment(environmentId);
        environment.getVariables().remove(key);
    }

    @Override
    public boolean environmentExists(String environmentId) {
        if(environmentRepository.existsById(UUID.fromString(environmentId)))
            return true;
        return false;
    }

    private Environment getEnvironment(String id)
    {
        return environmentRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new EnvironmentNotFoundException("Cannot find environment with id :" + id));
    }
}
