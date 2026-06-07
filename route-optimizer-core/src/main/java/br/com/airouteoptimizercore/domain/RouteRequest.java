package br.com.airouteoptimizercore.domain;

import br.com.airouteoptimizercore.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "route_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RouteRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "internal_id", nullable = false, updatable = false)
    private UUID internalId;

    @Column(name = "external_request_id", nullable = false)
    private String externalRequestId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "result_path")
    private String resultPath;
}
