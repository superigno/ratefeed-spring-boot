package com.pc.globalpos.ratefeed.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cube")
@XmlAccessorType(XmlAccessType.NONE)
public class EcbFeedDetail {
	
	@XmlAttribute(name="currency")
	private String currency;
	
	@XmlAttribute(name="rate")
	private String rate;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}			

}
