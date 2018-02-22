package com.woowahan.woowahan2018.support;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.IOException;

public class ErrorMessageContainer {
	private static final Logger log = LoggerFactory.getLogger(ErrorMessageContainer.class);
	private JSONObject messages;

	public ErrorMessageContainer(ResourceLoader resourceLoader) {
		JSONParser jsonParser = new JSONParser();
		Resource resource = resourceLoader.getResource("classpath:static/message.json");

		try {
			messages = (JSONObject) jsonParser.parse(new FileReader(resource.getFile()));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.debug("messages: {}", messages.get("EMAIL"));
		log.debug("messages: {}", ((JSONObject) messages.get("EMAIL")).get("EMPTY"));
	}

	public String getMessage(String keys) {
		if (messages == null) return "";

		String[] keyArray = keys.split("\\.");
		JSONObject temp = messages;

		for (String key: keyArray) {
			try {
				temp = (JSONObject) temp.get(key);
			} catch (ClassCastException e) {
				return (String) temp.get(key);
			}
		}

		return "";
	}
}
