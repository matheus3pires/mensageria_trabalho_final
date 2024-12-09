# Controle de Manutenção de Drones

## Descrição do Projeto
Este projeto é um sistema de mensageria para o Controle de Manutenção de Drones, desenvolvido utilizando JavaFX, RabbitMQ, e PostgreSQL. O objetivo é registrar drones, monitorar suas manutenções periódicas e alertar os operadores sobre serviços pendentes.

O sistema segue uma arquitetura cliente-servidor, onde todas as comunicações são realizadas via mensageiro (RabbitMQ). Ele implementa um CRUD simples para cadastro de drones, agendamento de manutenções, e consulta do histórico de revisões.

## Funcionalidades
1. Cadastro de Drones
   - Registre informações dos drones no sistema.
   
2. Agendamento de Manutenções
   - Planeje manutenções periódicas ou específicas para cada drone.

3. Histórico de Revisões
   - Consulte o histórico completo de manutenções realizadas.

## Arquitetura
1. Cliente (JavaFX)
   - Interface gráfica que permite interação com o sistema, como registro e consulta de dados.
   - Comunicação com o servidor através de mensagens enviadas ao mensageiro (RabbitMQ).

2. Servidor (Spring Boot)
   - Gerencia a lógica de negócios e a comunicação com o banco de dados relacional.
   - Implementa @RabbitListener para consumir mensagens do cliente.
   - Realiza operações no banco de dados (PostgreSQL).

3. Mensageiro (RabbitMQ)
   - Responsável pela troca de mensagens entre o cliente e o servidor, garantindo comunicação eficiente e em tempo real.

4. Banco de Dados Relacional (PostgreSQL)
   - Armazena os dados de drones, manutenções agendadas e histórico de revisões.

## Requisitos para Execução
Pré-requisitos
- Java 17+
- Maven
- RabbitMQ
- PostgreSQL (ou outro banco de dados relacional, configurado no servidor)
- Git (opcional para clonar o repositório)

## Configuração do Ambiente
1. Clone o repositório do projeto:
   ```bash
   git clone https://github.com/matheus3pires/mensageria_trabalho_final.git

2. Configure o banco de dados:
    -  Atualize as credenciais no arquivo application.properties do servidor:
    ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/Mensageria
    spring.datasource.username=<usuario>
    spring.datasource.password=<senha>

    
3. Configure o mensageiro (RabbitMQ ou Kafka):
    -  Certifique-se de que o serviço está em execução.
    -  Atualize as credenciais no arquivo application.properties do servidor para conectar ao mensageiro.
    ```bash
    spring.rabbitmq.host=jackal-01.rmq.cloudamqp.com
    spring.rabbitmq.port=5671
    spring.rabbitmq.username=btpkpnfb
    spring.rabbitmq.password=Z67E1BPTzKwF7kyyZJqZ44GBJ3EXWXQ2
    spring.rabbitmq.virtual-host=btpkpnfb
    spring.rabbitmq.ssl.enabled=true

    spring.rabbitmq.template.default-receive-queue=queue-massive
    spring.rabbitmq.listener.simple.prefetch=10

    queue.drones.name = mensageria_drones
    queue.manutencoes.name = mensageria_manutencoes
    
4. Compile e execute.

## Critérios de Avaliação Atendidos
- Funcionamento Técnico:
  - Integração completa do mensageiro (RabbitMQ ou Kafka) com CRUD.
  - Banco de dados atualizado via listeners.
  - Comunicação em tempo real entre cliente e servidor.
- Interface do Cliente (JavaFX):
  - Layout organizado e fácil de usar.
  - Funcionalidade completa para operações de CRUD.
- Integração com Banco de Dados:
  - Configuração eficiente e refletindo alterações via mensageria.
- Código Claro e Organizado:
  - Segue boas práticas de programação.

## Autores
-  Matheus Pires Santos.
-  Ryan Silva Marques.
