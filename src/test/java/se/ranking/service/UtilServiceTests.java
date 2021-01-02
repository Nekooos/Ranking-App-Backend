package se.ranking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UtilServiceTests {

    UtilServiceImpl utilService;

    @BeforeEach
    public void setup() {
        utilService = new UtilServiceImpl();
    }

    @Test
    public void convertStaticApneaTimeToPoints() {
        String value = "4:33.5";
        double points = utilService.convertStaticApneaTimeToPoints(value);
        Assertions.assertEquals(54.8, points);
    }

    @Test
    public void convertConstantWeightMetersToPoints() {
        String value = "55";
        double points = utilService.convertConstantWeightMetersToPoints(value);
        Assertions.assertEquals(55.0, points);
    }

    @Test
    public void convertDynamicApneaMetersToPoints() {
        String value = "55";
        double points = utilService.convertDynamicApneaMetersToPoints(value);
        Assertions.assertEquals(27.5, points);
    }

    @Test
    public void convertStringToSeconds() {
        double seconds = utilService.convertStringToSeconds("0:60.0");
        Assertions.assertEquals(60, seconds);

        double seconds2 = utilService.convertStringToSeconds("5:33.4");
        Assertions.assertEquals(333.4, seconds2);
    }
}
