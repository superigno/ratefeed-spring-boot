package com.pc.globalpos.ratefeed.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Sender")
@XmlAccessorType(XmlAccessType.NONE)
public class Sender {
	
	@XmlElement(name="name", namespace="http://www.gesmes.org/xml/2002-08-01")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
