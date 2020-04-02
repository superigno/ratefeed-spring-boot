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

import com.pc.globalpos.ratefeed.Application;
import com.pc.globalpos.ratefeed.core.XmlConverter;
import com.pc.globalpos.ratefeed.model.ecb.EcbFeed;
import com.pc.globalpos.ratefeed.model.ecb.EcbFeedDetail;
import com.pc.globalpos.ratefeed.model.ecb.EcbRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.model.ecb.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class XmlConverterTest {

	private static final String XML_FILE = Paths.get("C:\\ratefeed\\ecb.xml").toString();
	private static final String XML_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

	@Autowired
	private XmlConverter converter;

    //@Test
	public void testMarshall() throws IOException {

		Envelope envelope = new Envelope();
		envelope.setSubject("Reference rates");
		Sender sender = new Sender();
		sender.setName("European Central Bank");
		envelope.setSender(sender);
		EcbRoot root = new EcbRoot();
		EcbFeed feed = new EcbFeed();
		EcbFeedDetail detail = new EcbFeedDetail();
		detail.setCurrency("USD");
		detail.setRate("1.0981");
		List<EcbFeedDetail> detailList = new ArrayList<>();
		detailList.add(detail);

		feed.setTime("2020-03-26");
		feed.setDetailList(detailList);

		List<EcbFeed> feedList = new ArrayList<>();
		feedList.add(feed);
		root.setFeedList(feedList);
		envelope.setRoot(root);

		converter.convertFromObjectToXml(envelope, XML_FILE);
	}

   @Test
	public void testUnmarshall() throws IOException {

		URL url = new URL(XML_URL);
		Envelope envelope = (Envelope) converter.convertFromXmlToObject(url);
		EcbRoot root = (EcbRoot) envelope.getRoot();
		List<EcbFeed> feedList = root.getFeedList();

		for (EcbFeed feed : feedList) {

			System.out.println("Time: " + feed.getTime());
			List<EcbFeedDetail> detailList = feed.getDetailList();

			for (EcbFeedDetail detail : detailList) {
				System.out.println("Currency: " + detail.getCurrency());
				System.out.println("Rate: " + detail.getRate());
				assertNotNull(detail.getCurrency());
				assertNotNull(detail.getRate());
			}

		}

	}

}
