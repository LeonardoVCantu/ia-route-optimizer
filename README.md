# Microserviço de Roteirização Inteligente

Este microsserviço é responsável por receber solicitações de entregas via API, gerenciar o fluxo de processamento de forma assíncrona e calcular as melhores rotas logísticas utilizando Inteligência Artificial. O sistema integra serviços robustos da AWS para criar uma arquitetura altamente escalável e resiliente.

## 🎯 Propósito do Repositório

O objetivo principal deste projeto é atuar como um **Portfólio Técnico de Conceitos**. O foco do desenvolvimento foi demonstrar proficiência prática em:
* **Arquitetura Cloud:** Integração nativa e estruturada utilizando serviços AWS.
* **Mensageria e Filas Assíncronas:** Desacoplamento de sistemas para alta disponibilidade, retenção de mensagens e resiliência.
* **Inteligência Artificial Aplicada:** Processamento local de modelos de linguagem e otimização para resolução de problemas logísticos complexos.

⚠️ **Aviso sobre Segurança:** Com o objetivo de manter o escopo estritamente focado nos pilares acima, **camadas de segurança tradicionais (como autenticação e autorização de APIs via OAuth2, JWT ou API Keys) não foram implementadas neste repositório**. Este projeto tem fins estritamente demonstrativos e não deve ser implantado diretamente em ambiente de produção sem a devida adição desses mecanismos de proteção.

---

## 🛠️ Ambiente de Desenvolvimento e Simulação

Para viabilizar a execução, testes e demonstração do projeto sem custos e sem a necessidade de uma conta real na nuvem, o ambiente foi projetado para rodar de forma 100% local:
* **AWS Cloud Simulation (LocalStack):** Utilizamos o **LocalStack** para emular localmente os serviços da AWS (SQS e S3). Isso permite testar o comportamento real da arquitetura em nuvem diretamente na máquina de desenvolvimento.
* **Massa de Dados:** O sistema opera utilizando **dados fictícios** e cenários simulados para validar as regras de negócio e o fluxo dos componentes.

---

## 🚀 Arquitetura e Fluxo do Sistema

O fluxo de dados da solução foi projetado seguindo o padrão de mensageria assíncrona para garantir que a API de entrada permaneça leve e disponível:

1. **Ingestão (REST API):** O microsserviço expõe um endpoint que recebe as requisições de roteirização contendo os dados dos pedidos, frotas e restrições.
2. **Mensageria (AWS SQS / LocalStack):** A API publica uma mensagem contendo o payload da solicitação em uma fila do **AWS SQS**. Isso libera a API de forma imediata para novas requisições, evitando *timeouts*.
3. **Processamento (Worker de IA):** Um serviço especialista (*Worker*) consome as mensagens da fila de forma assíncrona. Ele é o responsável por orquestrar as regras e acionar a inteligência artificial.
4. **Motor de IA (Ollama):** O processamento inteligente e a análise de restrições são realizados localmente através do **Ollama**, permitindo que o modelo de IA interprete o cenário e apoie a tomada de decisão da rota ótima.
5. **Regras de Negócio Aplicadas:** Os cálculos consideram variáveis complexas em tempo real, tais como:
   * **Especificações dos Veículos:** Capacidade de carga, tipo de transporte e restrições de tráfego.
   * **Cubagem e Dimensões:** Tamanho e peso das embalagens para otimização do espaço físico.
   * **Regras Operacionais:** Janelas de entrega e restrições geográficas.
6. **Persistência (AWS S3 / LocalStack):** Após o processamento, o resultado detalhado da rota gerada é salvo como um objeto JSON em um bucket do **AWS S3**.
7. **Consumo Posterior:** O resultado fica disponível no S3 pronto para ser consumido sob demanda por sistemas parceiros, dashboards ou aplicativos mobile através de consultas futures.

---

## 🧰 Tecnologias Utilizadas

* **Ollama:** Execução local de modelos de Inteligência Artificial para análise e otimização.
* **LocalStack:** Emulação local dos serviços AWS para fins demonstrativos e de teste.
* **AWS SQS:** Gerenciamento de filas assíncronas e desacoplamento arquitetural.
* **AWS S3:** Armazenamento de objetos para persistência dos resultados de rotas gerados.
* **REST API:** Interface de entrada de dados de alta performance.

---

> 💡 **Nota Arquitetural:** Este design orientado a eventos garante que picos massivos de requisições na API de entrada não sobrecarreguem o motor de processamento de IA. O SQS atua como um amortecedor, permitindo uma escalabilidade elástica, econômica e auditável através do histórico armazenado no S3.
