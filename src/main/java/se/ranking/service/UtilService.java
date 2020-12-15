package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;

public interface UtilService<T> {
    List<T> combineLists(List<T> list1, List<T> list2);
    double convertStaticApneaTimeToPoints(String time);
}
