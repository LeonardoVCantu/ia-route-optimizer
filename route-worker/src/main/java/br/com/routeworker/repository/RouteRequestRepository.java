package br.com.routeworker.repository;

import br.com.airouteoptimizercore.domain.RouteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRequestRepository extends JpaRepository<RouteRequest, String> {
    Optional<RouteRequest> findByExternalRequestId(String requestId);
}
