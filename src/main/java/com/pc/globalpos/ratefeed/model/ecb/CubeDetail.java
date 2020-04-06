package com.pc.globalpos.ratefeed.model.ecb;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cube")
@XmlAccessorType(XmlAccessType.NONE)
public class CubeDetail {
	
	@XmlAttribute(name="currency")
	private String currency;
	
	@XmlAttribute(name="rate")
	private BigDecimal rate;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}				

}
