package org.restify.environment_service.Controller;

import org.restify.environment_service.Payload.Request.EnvironmentRequest;
import org.restify.environment_service.Payload.Response.EnvironmentResponse;
import org.restify.environment_service.Service.EnvironmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/environment")
@RequiredArgsConstructor
public class EnvironmentController {
    
    private final EnvironmentService environmentService;

    @PostMapping
    public ResponseEntity<Void> createAnEnvironment(@RequestBody EnvironmentRequest environmentRequest) {
        environmentService.createAnEnvironment(environmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/variable")
    public ResponseEntity<Void> addOrUpdateVariable(@PathVariable("id") String environmentId,
                                                     @RequestParam String key,
                                                     @RequestParam String value) {
        environmentService.addOrUpdateVariable(environmentId, key, value);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentResponse> getSpecificEnvironment(@PathVariable("id") String environmentId) {
        EnvironmentResponse response = environmentService.getSpecificEnvironment(environmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EnvironmentResponse>> getAllEnvironment(@RequestParam(required = false) String filter,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(environmentService.getAllEnvironment(filter, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnEnvironment(@PathVariable("id") String environmentId) {
        environmentService.deleteAnEnvironment(environmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/clone/{id}")
    public ResponseEntity<Void> cloneAnEnvironment(@PathVariable("id") String environmentId) {
        environmentService.cloneAnEnvironment(environmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<String> exportEnvironment(@PathVariable("id") String environmentId) {
        String exportedData = environmentService.exportEnvironment(environmentId);
        return ResponseEntity.ok(exportedData);
    }

    @PostMapping("/import/{id}")
    public ResponseEntity<Void> importEnvironment(@PathVariable("id") String fileId) {
        environmentService.importEnvironment(fileId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}/variable")
    public ResponseEntity<Void> deleteVariable(@PathVariable("id") String environmentId,
                                                @RequestParam String key) {
        environmentService.deleteVariable(environmentId, key);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> environmentExists(@PathVariable("id") String environmentId) {
        boolean exists = environmentService.environmentExists(environmentId);
        return ResponseEntity.ok(exists);
    }
}
