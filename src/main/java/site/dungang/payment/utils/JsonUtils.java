package site.dungang.payment.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	@SuppressWarnings("unchecked")
	public static <L, T> List<T> parseJson2List(String jsonString, Class<L> listClz, Class<T> clz) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = getCollectionType(listClz, clz);
		List<T> list = null;
		try {
			list = (List<T>) mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	/**
	 * 将json字符串转换城list集合
	 * 
	 * @param jsonString
	 * @param listClass
	 * @param clz
	 * @return
	 */
	public static <T> List<T> parseJson2ArrayList(String jsonString, Class<T> clz) {
		return parseJson2List(jsonString, ArrayList.class, clz);
	}

	/**
	 * 将json字符串转换成map集合
	 * 
	 * @param jsonString
	 * @param mapClz
	 * @param clz
	 * @return
	 */
	public static <K, T> Map<K, T> parseJson2HashMap(String jsonString, Class<T> clz) {
		return parseJson2Map(jsonString,HashMap.class,clz);
	}

	@SuppressWarnings("unchecked")
	public static <M, K, T> Map<K, T> parseJson2Map(String jsonString, Class<M> mapClz, Class<T> clz) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = getCollectionType(mapClz, clz);
		Map<K, T> map = null;
		try {
			map = (Map<K, T>) mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return map;
	}
	/**
	 * json转换城对象
	 * 
	 * @param jsonString
	 * @param clz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T parseJson2Class(String jsonString, Class<T> clz) {
		ObjectMapper mapper = new ObjectMapper();
		T o;
		try {
			o = mapper.readValue(jsonString, clz);
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			return null;
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
		return o;
	}
	
	public static <T> T toBean(String jsonString, Class<T> clz) {
		return JsonUtils.parseJson2Class(jsonString, clz);
	}
	
	/**
	 * 对象转换城json字符
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * json格式的字符串转换为map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> parseJson2map(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	/**
	 * json格式的字符串转换为map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, String> json2map(String json) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 字符串转换为map eg: "1:双人床|2:单人床|3:圆床|4:榻榻米"
	 * 
	 * @param string
	 * @return
	 */
	public static Map<String, String> string2map(String string) {
		String[] strs = string.split("\\|");
		Map<String, String> map = new HashMap<String, String>();
		for (String s : strs) {
			String[] ms = s.split(":");
			map.put(ms[0], ms[1]);
		}
		return map;
	}
	
	
}
