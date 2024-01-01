package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        try {
            con.setAutoCommit(false); //트랜잭션 시작
            // 비지니스 로직
            bizLogic(con, fromId, toId, money);
            con.commit(); // 성공시 커밋
        } catch (Exception e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    // 비지니스 로직
    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        // 이체를 하는 회원과 받는 회원의 정보 조회
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        // 이체를 하는 회원의 잔액 업데이트
        memberRepository.update(con, fromId, fromMember.getMoney() - money);

        // 오류 케이스
        validation(toMember);

        // 이체를 받는 회원의 잔액 업데이트
        memberRepository.update(con, toId, toMember.getMoney() + money);
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
