package br.com.ingestionapi.repository;

import br.com.airouteoptimizercore.domain.RouteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRequestRepository extends JpaRepository<RouteRequest, String> {
    boolean existsByExternalRequestId(String requestId);
}
