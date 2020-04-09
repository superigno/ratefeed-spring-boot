package com.pc.globalpos.ratefeed.ecb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.config.ApplicationConfig;
import com.pc.globalpos.ratefeed.main.EcbRateFeed;
import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.source.RateSource;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class EcbServiceImplTest {
	
	@Autowired
	ApplicationProperties props;
	
	@Autowired
	private RateSource<Envelope> rateSource;
	
	@MockBean
	private EcbRateFeed ecbRateFeed;
	
	@Test
	public void testGetFeed() throws IOException {
		Envelope envelope = (Envelope) rateSource.getFeed(props.getSourceUrl());
		int detailsSize = envelope.getCubeRoot().getCubeBranchList().get(0).getDetailList().size();
		assertNotNull(envelope);
		assertTrue(detailsSize > 0);
	}
	
	@Test
	public void testSaveFeed() throws IOException {
		Envelope feed = rateSource.getFeed(props.getSourceUrl());
		String strFeed = rateSource.parse(feed);
		String[] outputDirs = props.getOutputDirs();	
		for (String outputDir : outputDirs) {
			System.out.println(outputDir.trim());
			rateSource.saveToFile(strFeed, Paths.get(outputDir.trim()).resolve(ApplicationConfig.getFilename(props.getFilenameFormat())));
		}
	}
	
	@Test
	public void testDivide() {
		MathContext precision = new MathContext(8, RoundingMode.DOWN);
		BigDecimal divRate = new BigDecimal("1").divide(new BigDecimal("3.6725"), precision);
		assertEquals("0.27229407", divRate.toString());
	}

}
