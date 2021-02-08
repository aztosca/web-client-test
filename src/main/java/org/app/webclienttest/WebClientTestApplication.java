package org.app.webclienttest;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebClientTestApplication {

	public static void main(String[] args) {
		BlockHound.install();
		SpringApplication.run(WebClientTestApplication.class, args);
	}
	
	static String endpoint  =  "https://postman-echo.com/get?name=value";

	@Bean
	public RouterFunction<ServerResponse> testWebClient(WebClient.Builder webClientBuilder) {
		
		WebClient webClient = webClientBuilder.build();
		
		HandlerFunction<ServerResponse> testHandler = new HandlerFunction<>() { 
			@Override
			public Mono<ServerResponse> handle(ServerRequest request) {

				Mono<String> stringMono = webClient.get().uri(endpoint)
						.retrieve().bodyToMono(String.class)
						.flatMap(body -> Mono.just("Modify the response " + body));

				return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
						.body(stringMono, String.class); 
			}
		};
		return RouterFunctions.route(GET("/test-web-client").and(
				accept(MediaType.TEXT_PLAIN)),testHandler::handle);  
	}
}
