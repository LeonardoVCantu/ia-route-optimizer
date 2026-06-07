package br.com.routeworker.listener;

import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.routeworker.service.RouteRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.text.MessageFormat;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteRequestListener {

    private final RouteRequestService routeRequestService;
    private final ObjectMapper objectMapper;

    @Value("${app.queues.pedidos-rota}")
    private String queueOrderName;


    @SqsListener(value = "${app.queues.pedidos-rota}", factory = "defaultSqsListenerContainerFactory")
    public void receiveRouteRequest(String message) {
        try {
            var routeRequest = objectMapper.readValue(message, RouteRequestDTO.class);

            routeRequestService.processRouteRequest(routeRequest);

        }catch (JsonProcessingException e) {
            log.error("Erro de desserialização", e);
            throw new RuntimeException("Falha ao converter JSON da rota", e);
        }  catch (Exception e) {
            log.error("Ocorreu um erro no processamento da fila: {}" , queueOrderName, e);
            throw e;
        }
    }
}
