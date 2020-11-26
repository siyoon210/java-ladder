package nextstep.ladder.view;

import nextstep.ladder.domain.ExecutionResults;
import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Name;
import nextstep.ladder.domain.Participants;
import nextstep.ladder.domain.Results;

import java.util.function.Consumer;

public class ConsoleResultView implements ResultView {
    private static final String HEADER_MSG = "실행 결과";
    private static final String LADDER_STICK = "|";
    private static final String EXIST_POINT = "-----";
    private static final String EMPTY_POINT = "     ";
    private static final String NAME_STRING_FORMAT = "%6s";
    private static final String CAN_NOT_FIND_PARTICIPANTS_ERR_MSG = "입력된 참가자 이름을 찾을 수 없습니다.";

    @Override
    public void printLadder(Participants participants, Ladder ladder, Results results) {
        StringBuilder resultBuilder = new StringBuilder();

        appendHeader(resultBuilder);
        appendParticipantNames(participants, resultBuilder);
        appendLadder(ladder, resultBuilder);
        appendResults(results, resultBuilder);

        System.out.println(resultBuilder.toString());
    }

    @Override
    public boolean printResult(ExecutionResults executionResults, Name nameOfWantToCheck) {
        if (executionResults.isAllKeyword(nameOfWantToCheck)) {
            executionResults.forEach((key, value) -> System.out.println(key.getValue() + " : " + value));
            return true;
        }

        executionResults.accept(nameOfWantToCheck, (key, value) -> {
            if (value == null) {
                System.out.println(CAN_NOT_FIND_PARTICIPANTS_ERR_MSG);
                return;
            }
            System.out.println(value);
        });
        return false;
    }

    private void appendHeader(StringBuilder resultBuilder) {
        resultBuilder.append(HEADER_MSG).append(System.lineSeparator());
    }

    private void appendParticipantNames(Participants participants, StringBuilder resultBuilder) {
        participants.namesValueForEach(appendName(resultBuilder));
        resultBuilder.append(System.lineSeparator());
    }

    private Consumer<String> appendName(StringBuilder resultBuilder) {
        return (String name) -> resultBuilder.append(String.format(NAME_STRING_FORMAT, name));
    }

    private void appendLadder(Ladder ladder, StringBuilder resultBuilder) {
        LineRenderer lineRenderer = LineRenderer.builder()
                .firstPartOfLine(() -> resultBuilder.append(EMPTY_POINT).append(LADDER_STICK))
                .point(point -> resultBuilder.append(point ? EXIST_POINT : EMPTY_POINT))
                .ladderStick(() -> resultBuilder.append(LADDER_STICK))
                .lastPartOfLine(() -> resultBuilder.append(System.lineSeparator()))
                .build();

        ladder.linesForEach(lineRenderer.renderLine());
    }

    private void appendResults(Results results, StringBuilder resultBuilder) {
        results.forEach(appendName(resultBuilder));
    }
}
