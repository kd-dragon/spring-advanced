package my.spring.advanced.trace.template;

import lombok.extern.slf4j.Slf4j;
import my.spring.advanced.trace.TraceStatus;
import my.spring.advanced.trace.logtrace.LogTrace;

@Slf4j
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    /**
     * 제네릭을 사용하여 반환 타입을 이후에 결정하도록 한다.
     * @param message
     * @return
     */
    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            // 로직 호출
            T result = call();

            trace.end((status));
            return result;

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    // 변화는 부분을 처리하는 추상 메서드
    protected abstract T call();
}
