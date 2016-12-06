package org.feuyeux.alog.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.alog.proto.LandingServiceGrpc;
import org.feuyeux.alog.proto.TalkRequest;
import org.feuyeux.alog.proto.TalkResponse;

import static org.feuyeux.alog.domain.ALog.TRACE;

@Slf4j
public class LandingServiceImpl implements LandingServiceGrpc.LandingService {
    @Override
    public void talk(TalkRequest request, StreamObserver<TalkResponse> responseObserver) {
        long requestId = System.nanoTime();
        long b = System.currentTimeMillis();
        final TalkResponse response = ProtocolBuilder.buildResponse(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        long e = System.currentTimeMillis();
        // TRACE|ALOG|{}|{}|{}|latency={}ms|{}|{}
        log.info(TRACE, NetworkManager.getLocalIp(), e, requestId, e - b, request.toString(), response.toString().replaceAll("[\\t\\n\\r]", " "));
    }
}