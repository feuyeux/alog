package org.feuyeux.alog.service;

import org.feuyeux.alog.proto.ResultType;
import org.feuyeux.alog.proto.TalkRequest;
import org.feuyeux.alog.proto.TalkResponse;
import org.feuyeux.alog.proto.TalkResult;

import java.util.HashMap;

public class ProtocolBuilder {
    public static TalkRequest buildRequest() {
        return TalkRequest.newBuilder()
                .setMeta("user=eric")
                .setData("query=ai,from=0,size=1000,order=x,sort=y")
                .build();
    }

    public static TalkResponse buildResponse(TalkRequest request) {
        TalkResponse.Builder response = TalkResponse.newBuilder();
        response.setStatus(200);
        for (int i = 0; i < 10; i++) {
            response.addResults(getTalkResult(request));
        }
        return response.build();
    }

    private static TalkResult getTalkResult(TalkRequest request) {
        HashMap<String, String> kv = new HashMap<>();
        kv.put("request-data", request.getData());
        kv.put("request-meta", request.getMeta());
        kv.put("timestamp", String.valueOf(System.nanoTime()));
        return TalkResult.newBuilder()
                .setId(System.nanoTime())
                .setType(ResultType.SEARCH)
                .putAllKv(kv)
                .build();
    }
}
