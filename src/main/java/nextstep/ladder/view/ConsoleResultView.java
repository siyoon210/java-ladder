package nextstep.ladder.view;

import nextstep.ladder.domain.ExecutionResults;
import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Name;
import nextstep.ladder.domain.Participants;
import nextstep.ladder.domain.Point;
import nextstep.ladder.domain.ResultCandidates;

import java.util.function.Consumer;

public class ConsoleResultView implements ResultView {
    private static final String HEADER_MSG = "실행 결과";
    private static final String LADDER_STICK = "|";
    private static final String EXIST_DIRECTION = "-----";
    private static final String EMPTY_DIRECTION = "     ";
    private static final String NAME_STRING_FORMAT = "%6s";
    private static final String CAN_NOT_FIND_PARTICIPANTS_ERR_MSG = "입력된 참가자 이름을 찾을 수 없습니다.";

    @Override
    public void printLadder(Participants participants, Ladder ladder, ResultCandidates resultCandidates) {
        StringBuilder resultBuilder = new StringBuilder();

        appendHeader(resultBuilder);
        appendParticipantNames(participants, resultBuilder);
        appendLadder(ladder, resultBuilder);
        appendResults(resultCandidates, resultBuilder);

        System.out.println(resultBuilder.toString());
    }

    private void appendHeader(StringBuilder resultBuilder) {
        resultBuilder.append(HEADER_MSG).append(System.lineSeparator());
    }

    private void appendParticipantNames(Participants participants, StringBuilder resultBuilder) {
        participants.namesValueForEach(appendString(resultBuilder));
        resultBuilder.append(System.lineSeparator());
    }

    private Consumer<String> appendString(StringBuilder resultBuilder) {
        return (String name) -> resultBuilder.append(String.format(NAME_STRING_FORMAT, name));
    }

    private void appendLadder(Ladder ladder, StringBuilder resultBuilder) {
        LineRenderer lineRenderer = LineRenderer.builder()
                .firstPartOfLine(() -> resultBuilder.append(EMPTY_DIRECTION))
                .point(appendPoint(resultBuilder))
                .lastPartOfLine(() -> resultBuilder.append(System.lineSeparator()))
                .build();

        ladder.linesForEach(lineRenderer.renderLine());
    }

    private Consumer<Point> appendPoint(StringBuilder resultBuilder) {
        return point -> {
            resultBuilder.append(LADDER_STICK);
            if (point.hasRightDirection()) {
                resultBuilder.append(EXIST_DIRECTION);
                return;
            }
            resultBuilder.append(EMPTY_DIRECTION);
        };
    }

    private void appendResults(ResultCandidates resultCandidates, StringBuilder resultBuilder) {
        resultCandidates.forEach(appendString(resultBuilder));
    }

    @Override
    public boolean printResult(ExecutionResults executionResults, Name nameOfWantToCheck) {
        if (isAllKeyword(nameOfWantToCheck)) {
            executionResults.forEach((key, value) -> System.out.println(key.getValue() + " : " + value.getValue()));
            return true;
        }

        executionResults.accept(nameOfWantToCheck, (key, value) -> {
            if (value == null) {
                System.out.println(CAN_NOT_FIND_PARTICIPANTS_ERR_MSG);
                return;
            }
            System.out.println(value.getValue());
        });
        return false;
    }

    private boolean isAllKeyword(Name nameOfWantToCheck) {
        return ConsoleInputView.ALL_KEYWORD.equals(nameOfWantToCheck.getValue());
    }

    @Override
    public boolean printResult(ExecutionResults executionResults, Name nameOfWantToCheck) {
        if (isAllKeyword(nameOfWantToCheck)) {
            executionResults.forEach((key, value) -> System.out.println(key.getValue() + " : " + value.getValue()));
            return true;
        }

        executionResults.accept(nameOfWantToCheck, (key, value) -> {
            if (value == null) {
                System.out.println(CAN_NOT_FIND_PARTICIPANTS_ERR_MSG);
                return;
            }
            System.out.println(value.getValue());
        });
        return false;
    }

    private boolean isAllKeyword(Name nameOfWantToCheck) {
        return ConsoleInputView.ALL_KEYWORD.equals(nameOfWantToCheck.getValue());
    }
}
