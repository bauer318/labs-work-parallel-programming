import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.rsreu.kibamba.labs01.CircleSurfaceCalculator;

import static org.junit.jupiter.api.Assertions.*;

public class CircleSurfaceCalculatorTest {
    CircleSurfaceCalculator circleSurfaceCalculator;
    private final double RADIUS = 2;

    @BeforeEach
    public void init() {
        circleSurfaceCalculator = new CircleSurfaceCalculator();
    }

    @RepeatedTest(50)
    public void calculateCircleSurfaceTest() {
        double expected = 12.5663;
        double actual = circleSurfaceCalculator.calculateCircleSurface(RADIUS);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void calculateCircleSurfaceTextException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            circleSurfaceCalculator.calculateCircleSurface(-2);
        });
        String expectedMessage = "Отрицательное значение радиуса";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
