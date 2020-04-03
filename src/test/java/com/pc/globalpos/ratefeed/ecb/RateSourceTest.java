package com.pc.globalpos.ratefeed.ecb;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.CubeBranch;
import com.pc.globalpos.ratefeed.model.ecb.CubeDetail;
import com.pc.globalpos.ratefeed.model.ecb.CubeRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.source.RateSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RateSourceTest {
	
	@Autowired
	RateSource ecbRateSource;
	
	@Autowired
	ApplicationProperties props;
	
	//@Test
	public void testGetfeed() throws IOException {
		URL url = new URL(props.getRatefeedUrl());
		Envelope envelope = (Envelope) ecbRateSource.getFeed(url);
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
	
	@Test
	public void testSaveFeed() throws IOException {
		URL url = new URL(props.getRatefeedUrl());
		Path path = Paths.get("C:\\ratefeed\\test.txt");
		Envelope envelope = (Envelope) ecbRateSource.getFeed(url);
		ecbRateSource.saveFeed(envelope, path);
	}

}
