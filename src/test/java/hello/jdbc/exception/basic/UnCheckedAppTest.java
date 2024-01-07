package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import hello.jdbc.exception.basic.UnCheckedAppTest.NetworkClient.Repository;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unchecked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
//            e.printStackTrace(); // 좋진 않음
            log.info("ex", e);
        }
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }

        static class Repository {
            public void call() {
                try {
                    runSQL();
                } catch (SQLException e) {
                    throw new RuntimSQLException(e);
                }
            }

            public void runSQL () throws SQLException {
                throw new SQLException("ex");
            }
        }

        static class RuntimeConnectException extends RuntimeException {
            public RuntimeConnectException(String message) {
                super(message);
            }
        }

        static class RuntimSQLException extends RuntimeException {
            public RuntimSQLException(Throwable cause) {
                super(cause);
            }
        }
    }
}
