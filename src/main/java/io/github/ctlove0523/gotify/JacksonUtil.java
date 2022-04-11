package io.github.ctlove0523.gotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chentong
 */
public class JacksonUtil {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String list2String(List<T> input) {
        try {
            return MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error("list2String json processing exception ", e);
        }
        return null;
    }

    public static <T> List<T> string2List(String jsonString, Class<T> cls) {
        try {
            return MAPPER.readValue(jsonString, getCollectionType(List.class, cls));
        } catch (IOException e) {
            log.error("string2List json processing exception ", e);
        }
        return null;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static String map2String(Map<String, Object> map) {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("map2String json processing exception ", e);
        }

        return null;
    }

    public static Map<String, Object> string2Map(String input) {
        try {
            return MAPPER.readValue(input, Map.class);
        } catch (IOException e) {
            log.error("string2Map json processing exception ", e);
        }

        return new HashMap<>();
    }

    public static <T> String object2String(T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object2String json processing exception ", e);
        }
        return "";
    }

    public static <T> T string2Object(String content, Class<T> clazz) {
        try {
            return MAPPER.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            log.error("string2Object json processing exception ", e);
        }
        return null;
    }

}

