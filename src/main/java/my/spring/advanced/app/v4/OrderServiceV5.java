package my.spring.advanced.app.v4;

import my.spring.advanced.trace.callback.TraceTemplate;
import my.spring.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute(this.getClass().getSimpleName() + ".orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
