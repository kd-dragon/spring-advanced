package my.spring.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import my.spring.advanced.trace.strategy.code.strategy.ContextV2;
import my.spring.advanced.trace.strategy.code.strategy.Strategy;
import my.spring.advanced.trace.strategy.code.strategy.StrategyLogic1;
import my.spring.advanced.trace.strategy.code.strategy.StrategyLogic2;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략 패턴 사용
     */

    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1실행");
            }
        });
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 2실행");
            }
        });
    }

    @Test
    void strategyV3() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
    }

}
