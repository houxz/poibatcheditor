package cn.emg.poibatcheditor.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTWriter;

import java.io.IOException;

public class JsonGeoSerializer extends JsonSerializer<Geometry> {

	@Override
	public void serialize(Geometry value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		String geoStr = new WKTWriter().write(value);
		gen.writeString(geoStr);
	}
}
