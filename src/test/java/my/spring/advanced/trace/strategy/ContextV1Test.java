package my.spring.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import my.spring.advanced.trace.strategy.code.strategy.ContextV1;
import my.spring.advanced.trace.strategy.code.strategy.Strategy;
import my.spring.advanced.trace.strategy.code.strategy.StrategyLogic1;
import my.spring.advanced.trace.strategy.code.strategy.StrategyLogic2;
import my.spring.advanced.trace.template.code.AbstractTemplate;
import my.spring.advanced.trace.template.code.SubClassLogic1;
import my.spring.advanced.trace.template.code.SubClassLogic2;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    /**
     * 전략 패턴
     *
     * 변하지 않는 부분을 'Context'라는 곳에 두고
     * 변하는 부분을 'Strategy' 라는 인터페이스를 만들고 해당 인터페이스를 구현하도록 문제를 해결
     * - 상속이 아니라 위임
     */
    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    /**
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        log.info("클래스 이름2={}", template2.getClass());
        template2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 전략 패턴 사용
     */

    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1={}", strategyLogic1.getClass());
        contextV1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        log.info("strategyLogic2={}", strategyLogic2.getClass());
        contextV2.execute();
    }

    @Test
    void strategyV3() {
        ContextV1 contextV1 = new ContextV1( new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        ContextV1 contextV2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });

        contextV1.execute();
        contextV2.execute();
    }

    /**
     * 익명 내부 클래스를 자바 8로 구현하기 위해 람다 사용.
     * 람다로 변경하려면 인터페이스에 메서드가 1개만 있으면 된다.
     */
    @Test
    void  strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        context2.execute();
    }
}
