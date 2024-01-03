package hello.jdbc.exception.basic;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {

    static class Service {

    }
    static class NetworkClient {
        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
    }
    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}
