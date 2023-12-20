package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtility;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    // 등록
    public Member save(Member member) throws SQLException {
        // sql 작성 : sql 쿼리 알아야함
        String sql = "insert into member(member_id, money) values (?, ?)";

        // 있어야 연결 가능
        Connection con = null;

        // DB에 쿼리 보냄
        PreparedStatement pstmt = null;

        // sql reception 이 올라오기때문에 try
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            // 입셉션 터지면 log 확인
            log.error("db error", e);
            // 예외를 밖으로 던짐
            throw e;
        } finally {
            // result set은 지금 없으니 null
            close(con, pstmt, null);
        }

    }

    // 조회
    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        // select query의 결과를 담고있는 통
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            // 한번 호출
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    // 수정
    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            pstmt.executeUpdate();
            // 결과값 확인
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            // 입셉션 터지면 log 확인
            log.error("db error", e);
            // 예외를 밖으로 던짐
            throw e;
        } finally {
            // result set은 지금 없으니 null
            close(con, pstmt, null);
        }
    }

    // 삭제
    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
            // 결과값 확인
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // 입셉션 터지면 log 확인
            log.error("db error", e);
            // 예외를 밖으로 던짐
            throw e;
        } finally {
            // result set은 지금 없으니 null
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {

        // 외부 리소스 사용 실제 TCP,IP커넥션 사용 close
        // 코드 안정성을 위해서 추가
        if (rs != null) {
            try {
                rs.close(); // SQLException
            } catch (SQLException e) {
                log.info("error", e); // 로그로 에러남김
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtility.getConnection();
    }
}
