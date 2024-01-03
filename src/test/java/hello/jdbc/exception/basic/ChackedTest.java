package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
public class ChackedTest {

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedExeption extends Exception {
        public MyCheckedExeption(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedExeption e) {
                throw new RuntimeException(e);
            }
        }

    }

    static class Repository {
        public void call() throws MyCheckedExeption {
            throw new MyCheckedExeption("ex");
        }

    }
}
