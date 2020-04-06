package com.pc.globalpos.ratefeed.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

	public void convertFromObjectToXml(final Object object, final String filepath) throws IOException {
		try (FileOutputStream os = new FileOutputStream(filepath)) {
			marshaller.marshal(object, new StreamResult(os));
		}
	}

	public Object convertFromXmlToObject(final String address) throws IOException {
		URL url = null;
		try {
			url = new URL(address);
			try (InputStream is = url.openStream()) {
				return unmarshaller.unmarshal(new StreamSource(is));
			} 
		} catch (MalformedURLException e) {
			try (FileInputStream is = new FileInputStream(address)) {
				return unmarshaller.unmarshal(new StreamSource(is));
			}
		}
		
	}

}