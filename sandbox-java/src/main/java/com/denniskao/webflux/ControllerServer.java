package com.denniskao.webflux;

import com.denniskao.HiddenClass;
import com.google.common.base.Preconditions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class ControllerServer {

    static class RequestInterceptingHandlerAdapter extends RequestMappingHandlerAdapter {

        @Override
        public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
            return super.handle(exchange, handler)
                    .doOnSubscribe(s -> {
                        System.out.println(this.getClass().getName() + ": doOnSubScribe");
                    }).doFinally(res -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(this.getClass().getName() + ": doFinally");
                    });
        }
    }

    static class InterceptingFilter implements WebFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            return chain.filter(exchange)
                    .doOnSuccess(data -> {
                        System.out.println(this.getClass().getName() + ": doOnSuccess");
                    })
                    .doFinally(data -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(this.getClass().getName() + ": doFinally");
                    });
        }
    }


    @Controller
    static class FooController {
        @RequestMapping("/foo")
        public ResponseEntity<String> getFoo() {
            return ResponseEntity.ok("ok");
        }
    }

    @Configuration
    static class Configurer extends DelegatingWebFluxConfiguration {

        @Bean
        FooController fooController() {
            return new FooController();
        }

        @Override
        protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
            return new RequestInterceptingHandlerAdapter();
        }
    }

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Configurer.class);
        ctx.refresh();
        var httpHandler = WebHttpHandlerBuilder.applicationContext(ctx)
                .filter(new InterceptingFilter())
                .build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create()
                .host("localhost")
                .port(9080)
                .handle(adapter)
                .accessLog(true)
                .bindNow()
                .onDispose()
                .block();
    }
}
