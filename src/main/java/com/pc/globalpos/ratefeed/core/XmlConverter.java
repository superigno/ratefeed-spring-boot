package com.pc.globalpos.ratefeed.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

@Component
public class XmlConverter {

	@Autowired
	private Marshaller marshaller;

	@Autowired
	private Unmarshaller unmarshaller;

	public void convertFromObjectToXml(Object object, String filepath) throws IOException {
		try (FileOutputStream os = new FileOutputStream(filepath)) {
			marshaller.marshal(object, new StreamResult(os));
		}
	}

	public Object convertFromXmlToObject(String xmlfile) throws IOException {
		try (FileInputStream is = new FileInputStream(xmlfile)) {
			return unmarshaller.unmarshal(new StreamSource(is));
		}
	}

	public Object convertFromXmlToObject(URL url) throws IOException {
		try (InputStream is = url.openStream()) {
			return unmarshaller.unmarshal(new StreamSource(is));
		}		
	}

}