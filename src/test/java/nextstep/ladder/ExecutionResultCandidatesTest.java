package nextstep.ladder;

import nextstep.ladder.domain.ExecutionResults;
import nextstep.ladder.domain.Height;
import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Name;
import nextstep.ladder.domain.Participants;
import nextstep.ladder.domain.ResultCandidate;
import nextstep.ladder.domain.ResultCandidates;
import nextstep.ladder.mock.MockPointsGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ExecutionResultCandidatesTest {
    @Test
    void resultJudgeTest() {
        //given
        Participants participants = Participants.of("pobi", "honux", "crong", "jk");
        Height height = Height.valueOf(5);
        Ladder ladder = Ladder.of(participants, height, new MockPointsGenerator());
        ResultCandidates resultCandidates = ResultCandidates.of(participants.getSizeOfPerson(), "꽝", "5000", "꽝", "3000");

        //when
        ExecutionResults executionResults = ladder.resultOf(participants, resultCandidates);

        //then
        assertAll(
                () -> executionResults.accept(Name.valueOf("pobi"), (key, value) -> assertThat(value).isEqualTo(ResultCandidate.valueOf("꽝"))),
                () -> executionResults.accept(Name.valueOf("honux"), (key, value) -> assertThat(value).isEqualTo(ResultCandidate.valueOf("3000"))),
                () -> executionResults.accept(Name.valueOf("crong"), (key, value) -> assertThat(value).isEqualTo(ResultCandidate.valueOf("꽝"))),
                () -> executionResults.accept(Name.valueOf("jk"), (key, value) -> assertThat(value).isEqualTo(ResultCandidate.valueOf("5000")))
        );
    }
}
