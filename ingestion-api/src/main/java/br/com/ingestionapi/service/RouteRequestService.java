package br.com.ingestionapi.service;

import br.com.airouteoptimizercore.domain.RouteRequest;
import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.airouteoptimizercore.enums.ProcessStatus;
import br.com.ingestionapi.exception.BusinessException;
import br.com.ingestionapi.repository.RouteRequestRepository;
import br.com.ingestionapi.service.interfaces.IRouteRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteRequestService implements IRouteRequestService {

    private final RouteRequestRepository routeRequestRepository;
    private final MessagePublisherService messagePublisherService;

    @Override
    @Transactional
    public String registerNewRequest(RouteRequestDTO request) {
        if(routeRequestRepository.existsByExternalRequestId(request.requestId())){
            throw new BusinessException("Já existe uma requisição com esse mesmo id: " + request.requestId(), HttpStatus.CONFLICT);
        }

        RouteRequest entity = RouteRequest.builder()
                .internalId(UUID.randomUUID())
                .externalRequestId(request.requestId())
                .status(ProcessStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        routeRequestRepository.save(entity);

        messagePublisherService.publishOptimizationRequest(request);

        return entity.getInternalId().toString();
    }
}
