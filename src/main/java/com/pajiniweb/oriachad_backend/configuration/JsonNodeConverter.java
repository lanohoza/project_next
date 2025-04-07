package com.pajiniweb.oriachad_backend.configuration;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(JsonNode jsonNode) {
		try {
			return objectMapper.writeValueAsString(jsonNode);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert JsonNode to String", e);
		}
	}

	@Override
	public JsonNode convertToEntityAttribute(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert String to JsonNode", e);
		}
	}
}