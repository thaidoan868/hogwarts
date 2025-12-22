package vn.conguyetduong.hogwarts.infra.client.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vn.conguyetduong.hogwarts.business.exception.OpenAiException;

@Component
public class OpenAiClient {
    private final WebClient openAiWebClient;

    public OpenAiClient(@Qualifier("openAiWebClient") WebClient openAiWebClient) {
        this.openAiWebClient = openAiWebClient;
    }

    public Mono<ChatResponse> post(String path, Object request) {
        return openAiWebClient.post()
                .uri(path)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(OpenAiErrorResponse.class)
                                .defaultIfEmpty(new OpenAiErrorResponse(
                                        new Error(
                                                "Unknown OpenAI error",
                                                "unknown",
                                                null
                                        )
                                ))
                                .flatMap(err -> Mono.error(
                                        new OpenAiException(
                                                response.statusCode().value(),
                                                err.error().message(),
                                                err.error().type(),
                                                err.error().code()
                                        )
                                ))
                )
                .bodyToMono(ChatResponse.class);
    }
}

