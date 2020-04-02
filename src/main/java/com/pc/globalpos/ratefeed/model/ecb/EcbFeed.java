package com.pc.globalpos.ratefeed.model.ecb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.NONE)
public class EcbFeed {
	
	@XmlAttribute(name="time")
	private String time;
	
	@XmlElement(name="Cube")
	private List<EcbFeedDetail> detailList;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<EcbFeedDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<EcbFeedDetail> detailList) {
		this.detailList = detailList;
	}
	
}
