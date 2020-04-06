package com.pc.globalpos.ratefeed.ecb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.source.RateSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcbServiceImplTest {
	
	@Autowired
	ApplicationProperties props;
	
	@Autowired
	private RateSource<Envelope> rateSource;
	
	private Envelope envelope;
	
	//@Test
	public void testGetFeed() throws IOException {
		envelope = (Envelope) rateSource.getFeed(props.getSourceUrl());
		int detailsSize = envelope.getCubeRoot().getCubeBranchList().get(0).getDetailList().size();
		assertNotNull(envelope);
		assertEquals(32, detailsSize);
	}
	
	@Test
	public void testSaveFeed() throws IOException {
		rateSource.getFeed(props.getSourceUrl());
		rateSource.parse();
		rateSource.saveToFile(Paths.get(props.getOutputDir(), props.getFilename()));
	}
	
	@Test
	public void testDivide() {
		MathContext precision = new MathContext(8, RoundingMode.DOWN);
		BigDecimal divRate = new BigDecimal("1").divide(new BigDecimal("3.6725"), precision);
		System.out.println("DIV RATE: "+divRate);
	}

}
