package my.spring.advanced.app.v3;

import lombok.RequiredArgsConstructor;
import my.spring.advanced.trace.TraceStatus;
import my.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin(this.getClass().getSimpleName() + ".orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e; // 예외는 그대로 흘러가게 둬야한다 (로그는 비즈니스 로직에 영향을 주면 안된다)
        }
    }
}
