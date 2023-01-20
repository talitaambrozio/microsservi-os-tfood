# Projeto de Microsserviços

Projeto de Microsserviços desenvolvido durante o curso de Microsserviços na prática: Implementando com Java e Spring, da Alura.

## Descrição do sistema 🔧
Foram desenvolvidos os seguintes microsserviços:

- Pedidos:  API para realização de pedidos, contém o CRUD básico, criação de pedidos, consultas, 
atualização e exclusão. Os pedidos contém id, data e hora da criação, itens e status.

- Pagamentos: API para realização de pagamentos de pedidos, contém o CRUD básico, criação de pagamentos, consultas, 
atualização e exclusão. A API de Pagamentos se comunica com a API de Pedidos, quando é efetuado um pagamento o status do pagamento 
do pedido que consta na API de Pedidos é atualizado para PAGO, essa comunicação é síncrona e feita através do OpenFeign.

- Server: Servidor Eureka para controle das instâncias de todos os microsserviços.

- Gateway: Aplicação para definição de um ponto chave de acesso aos microsserviços. O gateway obtém a lista de endereços de todos os 
serviços registrados no Eureka Server, configura uma rota dinâmica para esses serviços e já faz o balanceamento de carga nas requisições.

## Tecnologias Utilizadas 
- Java 11
- Maven
- Spring Boot 3.0.1 e 2.6.7
- Flyway 
- Eureka Server
- MySQL
- Lombok
- JPA
- ModelMapper
- OpenFeign 3.1.2
- Resilience4J
- Gateway
