package io.github.ctlove0523.gotify;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author chentong
 */
public class JacksonUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> String list2String(List<T> input) {
		try {
			return MAPPER.writeValueAsString(input);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> string2List(String jsonString, Class<T> cls) {
		try {
			return MAPPER.readValue(jsonString, getCollectionType(List.class, cls));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static String map2String(Map<String, Object> map) {
		try {
			return MAPPER.writeValueAsString(map);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Map<String, Object> string2Map(String input) {
		try {
			return MAPPER.readValue(input, Map.class);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new HashMap<>();
	}

	public static <T> String object2String(T object) {
		try {
			return MAPPER.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static <T> T string2Object(String content, Class<T> clazz) {
		try {
			return MAPPER.readValue(content, clazz);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}

