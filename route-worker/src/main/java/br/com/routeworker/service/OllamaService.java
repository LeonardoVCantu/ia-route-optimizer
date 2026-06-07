package br.com.routeworker.service;

import br.com.airouteoptimizercore.dto.OptimizedRouteResponseDTO;
import br.com.airouteoptimizercore.dto.RouteRequestDTO;
import br.com.routeworker.service.Interface.IAiService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaService implements IAiService {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = """
        Você é o Motor de Otimização.
        Sua tarefa é resolver o Problema de Roteamento (VRP) baseando-se no 'OptimizationType'.
        
        ### ESTRATÉGIAS DE PRIORIZAÇÃO:
        1. DISTANCE: Foco na menor quilometragem total.
        2. TIME: Foco no término mais cedo das entregas.
        3. FUEL_CONSUMPTION: Foco em veículos com menor 'litersPerKm'.
        
        ### REGRAS RÍGIDAS:
        - Nunca exceda 'maxWeight' e 'maxVolume'.
        - PICKUP aumenta carga, DELIVERY diminui.
        - Respeite as janelas de horário e o endereço inicial do veículo.
        
        ### DIRETRIZES DE OTIMIZAÇÃO:
        1. SEQUÊNCIA LÓGICA: Utilize os endereços para criar caminhos que minimizem o deslocamento desnecessário.
        2. CAPACIDADE: Nunca exceda o 'maxWeight' e 'maxVolume' de um veículo.
        3. FLUXO PICKUP/DELIVERY:
           - Ao realizar um PICKUP, o peso/volume no veículo aumenta.
           - Ao realizar um DELIVERY, o peso/volume diminui.
           - O veículo deve ter espaço suficiente ANTES de realizar um PICKUP.
        4. JANELAS DE TEMPO: Respeite o 'availableFrom' e 'availableTo' do veículo e as janelas de cada parada. Considere o 'estimatedServiceTime' em cada parada para calcular a chegada na próxima.
        5. PONTO DE PARTIDA: Toda rota deve começar no endereço inicial do veículo.
        
        ### REGRAS DE SAÍDA:
        - Se uma parada não puder ser atendida por restrição de tempo ou capacidade, coloque-a na lista 'unassignedStopIds'.
        - Veículos sem paradas atribuídas devem ir para 'idleVehicleIds'.
        - Retorne estritamente o JSON seguindo o esquema fornecido. Não adicione texto explicativo fora do JSON.
        
        """;

    public OllamaService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }

    @Override
    public OptimizedRouteResponseDTO processRouteOptimizer(@NotNull RouteRequestDTO request) {
        var converter = new BeanOutputConverter<>(OptimizedRouteResponseDTO.class);

        return this.chatClient.prompt()
                .user(u -> u.text("""
                        ID: {requestId}
                        Estratégia: {strategy}
                        Dados: {data}
                        Formato: {format}
                        """)
                        .param("requestId", request.requestId())
                        .param("strategy", request.config().optimizeBy())
                        .param("data", request)
                        .param("format", converter.getFormat()))
                .options(OllamaOptions.builder()
                        .temperature(0.1)
                        .numCtx(8192)
                        .topP(0.9)
                        .build())
                .call()
                .entity(converter);
    }
}
