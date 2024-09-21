package org.restify.collection_service.EntityRepository;

import java.util.List;
import java.util.UUID;

import org.restify.collection_service.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,UUID>{    
    List<Request> findByCollectionId(UUID collectionId);
    List<Request> findByUrl(String url);
}
