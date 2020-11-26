package nextstep.ladder.view;

import java.util.List;

public interface InputView {
    List<String> getParticipantNames();

    int getHeight();

    List<String> getResults();

    String getNameOfWantToCheck();

    String getNameOfWantToCheck();

    void printError(RuntimeException e);
}
