package nextstep.ladder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PointsTest {
    @Test
    @DisplayName("Points의 사이즈는 참가인원(CountOfPerson)보다 1 적어야 한다.")
    void pointsSizeTest() {
        CountOfPerson countOfPerson = CountOfPerson.of(4);
        Points points = new Points(countOfPerson);
        assertThat(points.size()).isEqualTo(countOfPerson.value - 1);
    }

    @Test
    @DisplayName("Points값은 연속된 true가 나오면 안된다.")
    void pointsValueTest() {
        CountOfPerson countOfPerson = CountOfPerson.of(10_000);
        assertThatCode(() -> new Points(countOfPerson))
                .doesNotThrowAnyException();
    }
}
