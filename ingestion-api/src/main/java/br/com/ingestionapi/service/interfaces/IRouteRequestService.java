package br.com.ingestionapi.service.interfaces;

import br.com.airouteoptimizercore.dto.RouteRequestDTO;

public interface IRouteRequestService {

    String registerNewRequest(RouteRequestDTO request);
}
