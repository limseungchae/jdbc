package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
public class MemberServiceV3_2 {

//    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, TransactionTemplate txTemplate, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 비지니스 로직
            bizLogic(fromId, toId, money);
            transactionManager.commit(status); // 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }
    }

    // 비지니스 로직
    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        // 이체를 하는 회원과 받는 회원의 정보 조회
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        // 이체를 하는 회원의 잔액 업데이트
        memberRepository.update(fromId, fromMember.getMoney() - money);

        // 오류 케이스
        validation(toMember);

        // 이체를 받는 회원의 잔액 업데이트
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    // 오류 케이스를 검증하는 메서드.
    private static void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("에치중 예외 발생");
        }
    }
}
