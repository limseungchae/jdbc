package hello.jdbc.exception.basic;

import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static hello.jdbc.exception.basic.CheckedAppTest.NetworkClient.Repository;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UnCheckedAppTest {

    @Test
    void checked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
                .isInstanceOf(Exception.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws SQLException, ConnectException {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }

        static class Repository {
            public void call() {
                runSQL();

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
            public RuntimSQLException(String message) {
                super(message);
            }
        }
    }
}
