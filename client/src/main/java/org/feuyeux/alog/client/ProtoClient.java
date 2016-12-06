package org.feuyeux.alog.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.log4j.Log4j2;
import org.feuyeux.alog.proto.LandingServiceGrpc;
import org.feuyeux.alog.proto.TalkRequest;
import org.feuyeux.alog.proto.TalkResponse;
import org.feuyeux.alog.proto.TalkResult;
import org.feuyeux.alog.service.ProtocolBuilder;

import java.util.concurrent.TimeUnit;

@Log4j2
public class ProtoClient {
    private ManagedChannel channel;
    private LandingServiceGrpc.LandingServiceBlockingStub landingStub;

    public ProtoClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        landingStub = LandingServiceGrpc.newBlockingStub(channel);
    }

    public TalkResponse talk(TalkRequest talkRequest) {
        return landingStub.talk(talkRequest);
    }

    public void close() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    public static void main(String[] args) {
        ProtoClient protoClient = new ProtoClient("100.81.4.101", 50061);
        TalkResponse talkResponse = protoClient.talk(ProtocolBuilder.buildRequest());
        final TalkResult result = talkResponse.getResults(0);
        log.info("status={}", talkResponse.getStatus());
        log.info("FIRST LINE: id={}, type={}, kv={}",
                result.getId(),
                result.getType(),
                result.getKv());
        log.debug("MY TEST: {}", () -> invokeMe());
        protoClient.close();
    }

    private static long invokeMe() {
        long me = System.nanoTime();
        log.info(me);
        return me;
    }
}
