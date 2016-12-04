package org.feuyeux.alog.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.alog.proto.LandingServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service
@Slf4j
public class TalkService {
    @Value("${alog.rpc.port}")
    private int port;

    private Server server;

    @PostConstruct
    public void init() {
        try {
            this.start();
            log.info("gRPC Server is launched.");
        } catch (Exception e) {
            log.info("gRPC Server launch failed", e);
        }
    }

    @PreDestroy
    public void destroy() {
        server.shutdown();
        log.info("gRPC Server is stopped.");
    }

    private void start() throws Exception {
        try {
            server = ServerBuilder.forPort(port)
                    .addService(LandingServiceGrpc.bindService(new LandingServiceImpl()))
                    .build()
                    .start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.warn("shutting down Google RPC Server since JVM is shutting down");
                server.shutdown();
                log.warn("Google RPC Server shut down");
            }));
            log.info("Google RPC Server is launched.");
        } catch (IOException e) {
            log.error("Google RPC Server is failed to launch.", e);
        }
    }
}