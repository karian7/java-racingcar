package study.step3.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * <pre>
 * * 주어진 횟수 동안 n대의 자동차는 전진 또는 멈출 수 있다.
 * * 사용자는 몇 대의 자동차로 몇 번의 이동을 할 것인지를 입력할 수 있어야 한다.
 * * 전진하는 조건은 0에서 9 사이에서 random 값을 구한 후 random 값이 4이상일 경우이다.
 * * 자동차의 상태를 화면에 출력한다. 어느 시점에 출력할 것인지에 대한 제약은 없다.
 * </pre>
 */
public class CarRacingTest {
    private CarRacing carRacing;
    private Circuit circuit;

    @Test
    @DisplayName("자동차 경주를 실행하면 예외가 발생하지 않는다")
    void startRacing() {
        setUpLapsAndCars(new TestingCar());
        setUpRacing();

        assertThatCode(carRacing::start).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("자동차 경주를 시작하면 자동차가 달린다.")
    void carMoved() {
        setUpLapsAndCars(new TestingCar());
        setUpRacing();

        carRacing.start();

        assertThat(theCar().getDistanceDriven()).isEqualTo(1);
    }

    @Test
    @DisplayName("자동차 경주 시작전엔 자동차가 달리지 않는다.")
    void carNotMoved() {
        setUpLapsAndCars(new TestingCar());
        setUpRacing();

        assertThat(theCar().getDistanceDriven()).isEqualTo(0);
    }

    private Car theCar() {
        return circuit.getCars().iterator().next();
    }

    @Test
    @DisplayName("경주시작 전엔 이동시도횟수가 0이다.")
    void zeroTryWhenBeforeStart() {
        setUpLapsAndCars(new study.step3.TestingCar());
        setUpRacing();

        assertThat(carRacing.getRecords().getTotalTry()).isEqualTo(0);
    }

    @Test
    @DisplayName("경주시작 후엔 이동시도횟수가 0이다.")
    void nonZeroTryWhenAfterStart() {
        setUpLapsAndCars(new study.step3.TestingCar());
        setUpRacing();

        carRacing.start();

        assertThat(carRacing.getRecords().getTotalTry()).isEqualTo(1);
    }

    private void setUpLapsAndCars(Car... cars) {
        this.circuit = new Circuit(new HashSet<>(Arrays.asList(cars)), 1);
    }

    private void setUpRacing() {
        this.carRacing = new CarRacing(circuit);
    }

    public static class TestingCar implements Car {
        private final String name;
        private int distanceDriven = 0;


        public TestingCar() {
            this("anonymous");
        }

        public TestingCar(String name) {
            this.name = name;
        }

        @Override
        public void move() {
            distanceDriven++;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getTotalTry() {
            return 0;
        }

        @Override
        public int getDistanceDriven() {
            return distanceDriven;
        }

        @Override
        public List<Boolean> takeDrivingRecordTake(int takes) {
            return null;
        }
    }

}
