Avaliação Java: SCCON - Geospatial (Lucas Lacerda)
A aplicação utiliza Java 21, Spring Boot 3.4.5, Maven, e SpringDoc OpenAPI (Swagger) para documentação e teste dos endpoints.

IDE utilizada: IntelliJ IDEA Ultimate 2025.1.

Pré-requisitos
Antes de executar a aplicação, certifique-se de ter os seguintes itens instalados:

Java 21: Baixe e instale o JDK 21.
Maven: Versão 3.9.x ou superior.

Estrutura do Projeto
src
├── main
│   ├── java
│   │   └── com/avaliacao_java/sccon
│   │       ├── ScconApplication.java
│   │       ├── controller/PersonController.java
│   │       ├── exception/ResourceNotFoundException.java
│   │       ├── model/Person.java
│   │       ├── service/PersonService.java
│   └── resources
│       └── application.properties
pom.xml
README.md


pom.xml: Configura o Spring Boot 3.4.5, Java 21, e dependências como SpringDoc OpenAPI.
application.properties: Define a porta do servidor (8080) e configurações do Swagger.
Endpoints: Implementados em PersonController.java, com documentação via Swagger.

Configuração do Ambiente
1. Configurar o JDK 21

No sistema:
Defina a variável de ambiente JAVA_HOME para o diretório do JDK 21 (ex.: C:\Program Files\Java\jdk-21).
Adicione %JAVA_HOME%\bin ao PATH (Windows) ou $JAVA_HOME/bin (Linux/Mac).



2. Instalar o Maven

Baixe o Maven de https://maven.apache.org/download.cgi e extraia-o.
Defina a variável de ambiente M2_HOME para o diretório do Maven (ex.: C:\apache-maven-3.9.9).
Adicione %M2_HOME%\bin ao PATH (Windows) ou $M2_HOME/bin (Linux/Mac).
Verifique a instalação com: mvn --version



Como Executar a Aplicação

Via IntelliJ IDEA

Abrir o projeto:
No IntelliJ IDEA, selecione File > Open e escolha o arquivo pom.xml na raiz do projeto.
O IntelliJ reconhecerá o projeto Maven e baixará as dependências automaticamente.


Sincronizar dependências:
Abra o Maven Tool Window (lado direito) e clique no ícone de Refresh (seta circular).


Executar a aplicação:
Abra o arquivo src/main/java/com/example/demo/DemoApplication.java.
Clique no ícone de Play ao lado do método main e selecione Run 'DemoApplication'.
Alternativamente, crie uma configuração:
Vá para Run > Edit Configurations > + > Spring Boot.
Defina Main class como com.example.demo.DemoApplication e JRE como JDK 21.
Clique em Run.




Verificar execução:
O console do IntelliJ mostrará logs do Spring Boot, indicando que o servidor Tomcat iniciou na porta 8080.



Via Linha de Comando

Navegar até o diretório do projeto:cd <CAMINHO_DO_PROJETO>


Compilar o projeto:mvn clean install


Executar a aplicação:mvn spring-boot:run


Verificar execução:
A aplicação iniciará na porta 8080. Veja os logs no terminal para confirmar.



Acessar a Documentação e Testar Endpoints
A aplicação usa o Swagger (SpringDoc OpenAPI) para documentação e teste dos endpoints.

Acessar o Swagger UI:

Abra um navegador e vá para:http://localhost:8080/swagger-ui.html


Você verá uma interface com todos os endpoints documentados.



Endpoints Disponíveis
Abaixo está uma lista dos principais endpoints disponíveis:



GET
/person
Retorna a lista de todas as pessoas, ordenada por nome.


POST
/person
Cria uma nova pessoa (ID é gerado automaticamente se não fornecido).


DELETE
/person/{id}
Remove a pessoa com o ID especificado.


PUT
/person/{id}
Atualiza todos os campos da pessoa com o ID especificado.


PATCH
/person/{id}
Atualiza campos específicos da pessoa com o ID especificado.


GET
/person/{id}
Retorna os detalhes da pessoa com o ID especificado.


GET
/person/{id}/age?output={days|months|years}
Calcula a idade da pessoa em dias, meses ou anos.


GET
/person/{id}/salary?output={min|full}
Calcula o salário da pessoa em reais (full) ou salários mínimos (min).



Códigos de erro:
404 Not Found: Pessoa não encontrada para o ID especificado.
409 Conflict: Tentativa de criar uma pessoa com ID já existente.
400 Bad Request: Parâmetro output inválido em /age ou /salary.

