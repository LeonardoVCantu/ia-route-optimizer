package br.com.ingestionapi.service;

import br.com.airouteoptimizercore.domain.RouteRequest;
import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.airouteoptimizercore.enums.ProcessStatus;
import br.com.ingestionapi.repository.RouteRequestRepository;
import br.com.ingestionapi.service.interfaces.IMessagePublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisherService implements IMessagePublisherService {

    private final ObjectMapper objectMapper;
    private final SqsTemplate sqsTemplate;

    @Value("${app.queues.pedidos-rota}")
    private String queueOrderName;

    @Override
    public void publishOptimizationRequest(RouteRequestDTO request) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(request);

            sqsTemplate.send(to -> to.queue(queueOrderName).payload(jsonPayload));

        } catch (JsonProcessingException e) {
            log.error("Erro de serialização para o ID: {}", request.requestId(), e);
            throw new RuntimeException("Erro ao formatar dados da rota");
        } catch (Exception e) {
            log.error("Erro de conexão com o SQS", e);
            throw new RuntimeException("Fila de processamento indisponível");
        }
    }
}
