package br.com.ingestionapi.service.interfaces;

import br.com.airouteoptimizercore.dto.RouteRequestDTO;

public interface IMessagePublisherService {
    void publishOptimizationRequest(RouteRequestDTO request);
}
