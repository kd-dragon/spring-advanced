package my.spring.advanced.app.v5;

import lombok.RequiredArgsConstructor;
import my.spring.advanced.trace.logtrace.LogTrace;
import my.spring.advanced.trace.template.AbstractTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {

        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute(this.getClass().getSimpleName() + ".orderItem()");
    }
}
