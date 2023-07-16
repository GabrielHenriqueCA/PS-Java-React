package br.com.banco.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bancoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banco API")
                        .description("API para gerenciamento de contas e transferências bancárias")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact()
                                .name("Equipe do Banco")
                                .email("contato@banco.com")
                                .url("https://www.banco.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do Banco API")
                        .url("https://github.com/GabrielHCA/PS-Java-React/documentacao-api"));
    }

    public void addDefaultResponses(Operation operation) {
        ApiResponses apiResponses = operation.getResponses();
        apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
        apiResponses.addApiResponse("201", createApiResponse("Objeto persistido!"));
        apiResponses.addApiResponse("204", createApiResponse("Objeto excluído!"));
        apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
        apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
        apiResponses.addApiResponse("403", createApiResponse("Acesso proibido!"));
        apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!"));
        apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));
    }


    /**
     * Cria uma resposta da API com a mensagem fornecida.
     */
    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}