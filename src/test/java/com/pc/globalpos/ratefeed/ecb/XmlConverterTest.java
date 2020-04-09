package com.pc.globalpos.ratefeed.ecb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.core.XmlConverter;
import com.pc.globalpos.ratefeed.main.EcbRateFeed;
import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.CubeBranch;
import com.pc.globalpos.ratefeed.model.ecb.CubeDetail;
import com.pc.globalpos.ratefeed.model.ecb.CubeRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.model.ecb.Sender;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class XmlConverterTest {

	private static final Path XML_FILE = Paths.get("C:","ratefeed", "output", "test.xml");
	
	@Autowired
	private XmlConverter converter;
	
	@Autowired
	private ApplicationProperties props;
	
	@MockBean
	private EcbRateFeed ecbRateFeed;

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
		detail.setRate(new BigDecimal("1.0981"));
		List<CubeDetail> detailList = new ArrayList<>();
		detailList.add(detail);

		branch.setTime("2020-03-26");
		branch.setDetailList(detailList);

		List<CubeBranch> branchList = new ArrayList<>();
		branchList.add(branch);
		root.setCubeBranchList(branchList);
		envelope.setCubeRoot(root);

		converter.convertFromObjectToXml(envelope, XML_FILE.toString());
		
		assertTrue(Files.exists(XML_FILE));
	}

    @Test
	public void testUnmarshall() throws IOException {

		Envelope envelope = (Envelope) converter.convertFromXmlToObject(props.getSourceUrl());
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
