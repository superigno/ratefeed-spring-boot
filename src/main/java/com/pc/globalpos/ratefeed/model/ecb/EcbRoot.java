package com.pc.globalpos.ratefeed.model.ecb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Cube")
@XmlAccessorType(XmlAccessType.NONE)
public class EcbRoot {
	
	@XmlElement(name="Cube")
	private List<EcbFeed> feedList;

	public List<EcbFeed> getFeedList() {
		return feedList;
	}

	public void setFeedList(List<EcbFeed> feedList) {
		this.feedList = feedList;
	}	

}
