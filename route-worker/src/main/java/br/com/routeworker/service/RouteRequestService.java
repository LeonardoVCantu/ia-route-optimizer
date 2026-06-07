package br.com.routeworker.service;

import br.com.airouteoptimizercore.dto.OptimizedRouteResponseDTO;
import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.airouteoptimizercore.enums.ProcessStatus;
import br.com.routeworker.repository.RouteRequestRepository;
import br.com.routeworker.service.Interface.IRouteRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteRequestService implements IRouteRequestService {

    private final ObjectMapper objectMapper;
    private final OllamaService ollamaService;
    private final RouteRequestRepository routeRequestRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public void processRouteRequest(RouteRequestDTO routeRequest) {
        var entity = routeRequestRepository.findByExternalRequestId(routeRequest.requestId())
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada: " + routeRequest.requestId()));

        try {
            entity.setStatus(ProcessStatus.PROCESSING);
            routeRequestRepository.save(entity);

            OptimizedRouteResponseDTO result = ollamaService.processRouteOptimizer(routeRequest);

            String jsonResult = objectMapper.writeValueAsString(result);

            String path = s3Service.upload("results/" + routeRequest.requestId() + ".json",
                    jsonResult,
                    MediaType.APPLICATION_JSON_VALUE);

            entity.setResultPath(path);
            entity.setStatus(ProcessStatus.COMPLETED);
            entity.setFinishedAt(LocalDateTime.now());

            routeRequestRepository.save(entity);

        } catch (Exception e) {
            log.error("Erro ao processar rota {}: {}", routeRequest.requestId(), e.getMessage());

            entity.setStatus(ProcessStatus.ERROR);
            routeRequestRepository.save(entity);

            throw new RuntimeException(e);
        }
    }
}
