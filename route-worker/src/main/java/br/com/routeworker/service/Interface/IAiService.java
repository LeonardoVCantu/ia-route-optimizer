package br.com.routeworker.service.Interface;

import br.com.airouteoptimizercore.dto.OptimizedRouteResponseDTO;
import br.com.airouteoptimizercore.dto.RouteRequestDTO;

public interface IAiService {
    OptimizedRouteResponseDTO processRouteOptimizer(RouteRequestDTO routeRequestDTO);
}
