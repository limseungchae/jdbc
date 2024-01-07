package hello.jdbc.exception.translator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExTranslatorV1Test {

    @RequiredArgsConstructor
    static class Repository {
        String sql = "insert into member(member_id, money) values(?,?)";

    }
}
