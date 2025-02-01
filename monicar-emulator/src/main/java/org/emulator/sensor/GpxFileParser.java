package org.emulator.sensor;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;
import org.emulator.sensor.dto.Gps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GpxFileParser {
	@Value("${file-name}")
	private String fileName;
	private final ResourceLoader resourceLoader;

	public Queue<Gps> parse() {
		Queue<Gps> gpsList = new ArrayDeque<>();

		// XML 파일 가져와서 구조화하기
		Document document;
		try {
			Resource resource = resourceLoader
				.getResource("classpath:gpx/"+ fileName);

			InputStream inputStream = resource.getInputStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(inputStream);
			document.getDocumentElement().normalize();

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), ErrorCode.FILE_LOAD_FILE);
		}


		// <trkpt> 태그 가져오기
		NodeList trkpts = document.getElementsByTagName("trkpt");

		for (int i = 0; i < trkpts.getLength(); i++) {
			Element trkpt = (Element) trkpts.item(i);
			double lat = Double.parseDouble(trkpt.getAttribute("lat"));
			double lng = Double.parseDouble(trkpt.getAttribute("lon"));
			gpsList.offer(new Gps(lat, lng));
		}

		return gpsList;
	}
}
