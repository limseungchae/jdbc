package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

@Slf4j
public class ChackedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedExeption extends Exception {
        public MyCheckedExeption(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘중 하나를 필수로 선택해야 한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedExeption e) {
                // 예외 처리 로직
                log.info("예외처리, message={}", e.getMessage(), e);
            }
        }

    }

    static class Repository {
        public void call() throws MyCheckedExeption {
            throw new MyCheckedExeption("ex");
        }

    }
}