package com.pc.globalpos.ratefeed.model.ecb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@XmlRootElement(name="Cube")
@XmlAccessorType(XmlAccessType.NONE)
public class CubeRoot {
	
	@XmlElement(name="Cube")
	private List<CubeBranch> cubeBranchList;

	public List<CubeBranch> getCubeBranchList() {
		return cubeBranchList;
	}

	public void setCubeBranchList(List<CubeBranch> cubeBranchList) {
		this.cubeBranchList = cubeBranchList;
	}	

}
