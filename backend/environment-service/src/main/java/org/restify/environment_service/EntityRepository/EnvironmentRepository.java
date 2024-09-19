package org.restify.environment_service.EntityRepository;

import java.util.Optional;
import java.util.UUID;

import org.restify.environment_service.Entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnvironmentRepository extends JpaRepository<Environment,UUID>{
    Optional<Environment> findByName(String name);
}
