package my.spring.advanced.app.v3;

import lombok.RequiredArgsConstructor;
import my.spring.advanced.trace.TraceStatus;
import my.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin(this.getClass().getSimpleName() + ".request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch(Exception e) {
            trace.exception(status, e);
            throw e; // 예외는 그대로 흘러가게 둬야한다 (로그는 비즈니스 로직에 영향을 주면 안된다)
        }
    }
}
