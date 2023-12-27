package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); //트랜잭션 시작
            // 비지니스 로직
            // 이체를 하는 회원과 받는 회원의 정보 조회
            Member fromMember = memberRepository.findById(fromId);
            Member toMember = memberRepository.findById(toId);

            // 이체를 하는 회원의 잔액 업데이트
            memberRepository.update(fromId, fromMember.getMoney() - money);

            // 오류 케이스
            validation(toMember);

            // 이체를 받는 회원의 잔액 업데이트
            memberRepository.update(toId, toMember.getMoney() + money);
            con.commit(); // 성공시 커밋
        } catch (Exception e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    // 커넥션을 해제하고 자동 커밋을 활성화하는 메서드
    private static void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    // 오류 케이스를 검증하는 메서드.
    private static void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("에치중 예외 발생");
        }
    }
}
