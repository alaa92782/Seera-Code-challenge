package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Constants;

import java.io.InputStream;

public class JsonBuilder {

	private static ObjectMapper mapper = new ObjectMapper();

	private JsonBuilder() {
	}

	public static JsonNode readJsonFile(String fileName) throws Exception {
		String filePath = filePath(fileName);
		InputStream stream = JsonBuilder.class.getResourceAsStream(filePath);
		return mapper.readValue(stream, JsonNode.class);
	}

	private static String filePath(String fileName) {
		if (fileName.endsWith(".json")) {
			return Constants.JSON_DIRECTORY + fileName;
		}

		return Constants.JSON_DIRECTORY + fileName + ".json";
	}
}