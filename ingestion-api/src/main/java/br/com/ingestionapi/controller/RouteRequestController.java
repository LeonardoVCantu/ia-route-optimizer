package br.com.ingestionapi.controller;

import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.airouteoptimizercore.dto.RouteRequestResponseDTO;
import br.com.ingestionapi.service.MessagePublisherService;
import br.com.ingestionapi.service.RouteRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("api/v1/routes")
@RequiredArgsConstructor
public class RouteRequestController {

    private final MessagePublisherService publisherService;
    private final RouteRequestService routeRequestService;

    @PostMapping("/optimize")
    public ResponseEntity<RouteRequestResponseDTO> optimize(@Valid @RequestBody RouteRequestDTO request) {

        String internalId = routeRequestService.registerNewRequest(request);

        RouteRequestResponseDTO response = new RouteRequestResponseDTO(
                request.requestId(),
                internalId,
                "Solicitação enviada com sucesso para processamento.",
                LocalDateTime.now()
        );

        return ResponseEntity.accepted().body(response);
    }

}
