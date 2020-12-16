package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UtilServiceImpl implements UtilService{

    public double convertStaticApneaTimeToPoints(String time) {
        double seconds = convertStringToSeconds(time);
        return roundDownToZeroPointTwo(seconds * 0.2);
    }

    public double convertConstantWeightMetersToPoints(String meters) {
        return Double.parseDouble(meters);
    }

    public double convertDynamicApneaMetersToPoints(String meters) {
        return roundToHalf(Double.parseDouble(meters) * 0.5);
    }

    private double convertStringToSeconds(String time) {
        String[] timeUnits = time.split("[:.]");
        String[] timeUnitsMilliSeconds = time.split("\\.");

        int minutes = Integer.parseInt(timeUnits[0]);
        int seconds = Integer.parseInt(timeUnits[1]);
        int milliseconds = Integer.parseInt(timeUnitsMilliSeconds[1]);
        return minutes * 60.0 + seconds + milliseconds / 10.0;
    }

    private double roundToHalf(double value) {
        return Math.round(value * 2) / 2.0;
    }

    private double roundDownToZeroPointTwo(double value) {
        return Math.round(value * 5.0) / 5.0;
    }

    /*
    3.2.5 Each performance is converted into points according to the following scale:
	 • Static Apnea: 1 second of immersion = 0.2 points,
	 • Depth Apnea: 1 meter in depth = 1.0 points,
	 • Dynamic Apnea: 1 meter in distance = 0.5 points.

     In depth disciplines, the performance is rounded down to the nearest point. In dynamic apnea, the
        performance is rounded down to the nearest 0.5 point. In static apnea, the performance is rounded down
        to the nearest 0.2 point.
         Examples:
        5’04” in static apnea = 60.8 points
        55.5m in constant weight = 55.0 points
        97.8m in dynamic apnea = 48.5 points
     */
}
