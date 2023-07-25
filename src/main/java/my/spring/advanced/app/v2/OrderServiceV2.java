package my.spring.advanced.app.v2;

import lombok.RequiredArgsConstructor;
import my.spring.advanced.trace.TraceId;
import my.spring.advanced.trace.TraceStatus;
import my.spring.advanced.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, this.getClass().getSimpleName() + ".orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e; // 예외는 그대로 흘러가게 둬야한다 (로그는 비즈니스 로직에 영향을 주면 안된다)
        }
    }
}
