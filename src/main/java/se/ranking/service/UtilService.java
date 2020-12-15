package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;

public interface UtilService {
    double convertStaticApneaTimeToPoints(String time);
    double convertConstantWeightMetersToPoints(String meters);
    double convertDynamicApneaMetersToPoints(String meters);
}
