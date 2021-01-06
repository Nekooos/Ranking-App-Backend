package se.ranking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilServiceTests {

    UtilServiceImpl utilService;

    @BeforeEach
    public void setup() {
        utilService = new UtilServiceImpl();
    }

    @Test
    public void stringToLocalDateTime() {
        String time = "2021-01-06";
        LocalDate localDate = utilService.stringToLocalDateTime(time);
        LocalDate expectedLocalDate = LocalDate.of(2021, 1, 6);
        assertEquals(expectedLocalDate, localDate);
    }

    @Test
    public void convertStaticApneaTimeToPoints() {
        String value = "4:33.5";
        double points = utilService.convertStaticApneaTimeToPoints(value);
        assertEquals(54.8, points);
    }

    @Test
    public void convertConstantWeightMetersToPoints() {
        String value = "55";
        double points = utilService.convertConstantWeightMetersToPoints(value);
        assertEquals(55.0, points);
    }

    @Test
    public void convertDynamicApneaMetersToPoints() {
        String value = "55";
        double points = utilService.convertDynamicApneaMetersToPoints(value);
        assertEquals(27.5, points);
    }

    @Test
    public void convertStringToSeconds() {
        double seconds = utilService.convertStringToSeconds("0:60.0");
        assertEquals(60, seconds);

        double seconds2 = utilService.convertStringToSeconds("5:33.4");
        assertEquals(333.4, seconds2);
    }
}
