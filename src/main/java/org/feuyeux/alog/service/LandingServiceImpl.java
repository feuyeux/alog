package org.feuyeux.alog.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.alog.proto.LandingServiceGrpc;
import org.feuyeux.alog.proto.TalkRequest;
import org.feuyeux.alog.proto.TalkResponse;
@Slf4j
public class LandingServiceImpl implements LandingServiceGrpc.LandingService {
    @Override
    public void talk(TalkRequest request, StreamObserver<TalkResponse> responseObserver) {
        log.debug("REQUEST:{}", request.toString());
        final TalkResponse response = ProtocolBuilder.buildResponse(request);
        log.debug("RESPONSE:{}", response.toString());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

