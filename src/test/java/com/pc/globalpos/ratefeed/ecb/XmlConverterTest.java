package com.pc.globalpos.ratefeed.ecb;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.core.XmlConverter;
import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.CubeBranch;
import com.pc.globalpos.ratefeed.model.ecb.CubeDetail;
import com.pc.globalpos.ratefeed.model.ecb.CubeRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.model.ecb.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlConverterTest {

	private static final String XML_FILE = Paths.get("C:","ratefeed","ecb.xml").toString();
	
	@Autowired
	private XmlConverter converter;
	
	@Autowired
	private ApplicationProperties props;

    @Test
	public void testMarshall() throws IOException {
    	
    	Envelope envelope = new Envelope();
		envelope.setSubject("Reference rates");
		Sender sender = new Sender();
		sender.setName("European Central Bank");
		envelope.setSender(sender);
		CubeRoot root = new CubeRoot();
		CubeBranch branch = new CubeBranch();
		CubeDetail detail = new CubeDetail();
		detail.setCurrency("USD");
		detail.setRate("1.0981");
		List<CubeDetail> detailList = new ArrayList<>();
		detailList.add(detail);

		branch.setTime("2020-03-26");
		branch.setDetailList(detailList);

		List<CubeBranch> branchList = new ArrayList<>();
		branchList.add(branch);
		root.setCubeBranchList(branchList);
		envelope.setCubeRoot(root);

		converter.convertFromObjectToXml(envelope, XML_FILE);
	}

    //@Test
	public void testUnmarshall() throws IOException {

		URL url = new URL(props.getRatefeedUrl());
		Envelope envelope = (Envelope) converter.convertFromXmlToObject(url);
		CubeRoot root = (CubeRoot) envelope.getCubeRoot();
		List<CubeBranch> branchList = root.getCubeBranchList();

		for (CubeBranch branch : branchList) {

			System.out.println("Time: " + branch.getTime());
			List<CubeDetail> detailList = branch.getDetailList();

			for (CubeDetail detail : detailList) {
				System.out.println("Currency: " + detail.getCurrency());
				System.out.println("Rate: " + detail.getRate());
				assertNotNull(detail.getCurrency());
				assertNotNull(detail.getRate());
			}

		}

	}

}
