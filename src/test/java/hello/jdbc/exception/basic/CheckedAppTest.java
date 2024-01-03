package hello.jdbc.exception.basic;

import java.sql.SQLException;

public class CheckedAppTest {

    static class Service {

    }
    static class NetworkClient {

    }
    static class Repository {
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}
