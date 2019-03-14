package cn.emg.poibatcheditor.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

import java.io.IOException;

public class JsonGeoDeserializer extends JsonDeserializer<Geometry> {

	@Override
	public Geometry deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		WKTReader wktReader = new WKTReader();
		try {
			return wktReader.read(node.asText());
		} catch (Exception e) {
			return null;
		}
	}
}
