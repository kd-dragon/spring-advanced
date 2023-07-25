package my.spring.advanced.app.v2;

import lombok.RequiredArgsConstructor;
import my.spring.advanced.trace.TraceId;
import my.spring.advanced.trace.TraceStatus;
import my.spring.advanced.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, this.getClass().getSimpleName() + ".save()");

            if (itemId.equalsIgnoreCase("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e; // 예외는 그대로 흘러가게 둬야한다 (로그는 비즈니스 로직에 영향을 주면 안된다)
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
