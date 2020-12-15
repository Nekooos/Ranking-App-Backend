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
public class UtilServiceImpl<T> implements UtilService<T>{

    @Override
    public List<T> combineLists(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }

    public double convertStaticApneaTimeToPoints(String time) {
        int seconds = convertStringToSeconds(time);
        return roundToHalf(seconds * 0.2);
    }

    private int convertStringToSeconds(String time) {
        String[] timeUnits = time.split(":");
        int minutes = Integer.parseInt(timeUnits[0]);
        int seconds = Integer.parseInt(timeUnits[1]);
        return minutes * 60 + seconds;
    }

    private double roundToHalf(double value) {
        return Math.round(value * 2) / 2.0;
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
        3.2.6 Determining the winner of an event
     */
}
