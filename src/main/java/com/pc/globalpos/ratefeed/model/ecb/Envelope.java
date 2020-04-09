package com.pc.globalpos.ratefeed.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@XmlRootElement(name="Envelope", namespace="http://www.gesmes.org/xml/2002-08-01")
@XmlAccessorType(XmlAccessType.NONE)
public class Envelope {
	
	@XmlElement(name="subject", namespace="http://www.gesmes.org/xml/2002-08-01")
	private String subject;
	
	@XmlElement(name="Sender", namespace="http://www.gesmes.org/xml/2002-08-01")
	private Sender sender;
	
	@XmlElement(name="Cube")
	private CubeRoot cubeRoot;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public CubeRoot getCubeRoot() {
		return cubeRoot;
	}

	public void setCubeRoot(CubeRoot cubeRoot) {
		this.cubeRoot = cubeRoot;
	}	

}
