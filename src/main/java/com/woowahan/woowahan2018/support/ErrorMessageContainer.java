package com.woowahan.woowahan2018.support;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ErrorMessageContainer {
	private static final Logger log = LoggerFactory.getLogger(ErrorMessageContainer.class);
	private JSONObject messages;

	public ErrorMessageContainer() {
		JSONParser jsonParser = new JSONParser();
		ClassPathResource classPathResource = new ClassPathResource("static/message.json");

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
			messages = (JSONObject) jsonParser.parse(reader);
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
