package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // 트랜잭션 시작
        // 이체를 하는 회원과 받는 회원의 정보 조회
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        // 이체를 하는 회원의 잔액 업데이트
        memberRepository.update(fromId, fromMember.getMoney() - money);

        // 오류 케이스
        validation(toMember);

        // 이체를 받는 회원의 잔액 업데이트
        memberRepository.update(toId, toMember.getMoney() + money);
        // 커밋, 롤백
    }

    // 오류 케이스를 검증하는 메서드.
    private static void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("에치중 예외 발생");
        }
    }
}
