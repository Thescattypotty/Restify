package org.restify.collection_service.EntityRepository;

import java.util.UUID;

import org.restify.collection_service.Entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.restify.collection_service.Entity.Request;



@Repository
public interface CollectionRepository extends JpaRepository<Collection,UUID>{
    List<Collection> findByEnvironment_id(String environment_id);
    List<Collection> findByRequests(List<Request> requests);
    Page<Collection> findByNameContainingOrDescriptionContaining(String name, String description , Pageable pageable);
}
