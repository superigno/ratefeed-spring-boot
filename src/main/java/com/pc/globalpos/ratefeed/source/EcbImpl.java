package com.pc.globalpos.ratefeed.source;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pc.globalpos.ratefeed.core.XmlConverter;
import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.model.ecb.CubeBranch;
import com.pc.globalpos.ratefeed.model.ecb.CubeDetail;
import com.pc.globalpos.ratefeed.model.ecb.CubeRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.util.FileUtils;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@Component
public class EcbImpl implements RateSource<Envelope> {
	
	private static final Logger logger = LoggerFactory.getLogger(EcbImpl.class);
	private Envelope feed;	
	private String formattedFeed;
	
	@Autowired
	private XmlConverter converter;
	
	@Autowired
	ApplicationProperties props;
	
	
	@Override
	public Envelope getFeed(final String url) throws IOException {
		logger.info("Retrieving feed from: {}", url);
		feed = (Envelope) converter.convertFromXmlToObject(url);
		logger.info("Done");
		return feed;
	}

	@Override
	public String parse(final Envelope feed) throws IOException {
		logger.info("Parsing feed...");
		final String baseCurrency = props.getBaseCurrency();
		final String sourceName = props.getSourceName();
		final String sourceType = props.getSourceType();
		final String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		final StringBuilder sb = new StringBuilder();
		final BigDecimal dividend = new BigDecimal("1");
		final MathContext precision = new MathContext(8, RoundingMode.DOWN);
		
		sb.append(sourceName);
		sb.append(System.lineSeparator());
		sb.append(sourceType);
		
		final CubeRoot root = (CubeRoot) feed.getCubeRoot();
		final List<CubeBranch> branchList = root.getCubeBranchList();
		for (CubeBranch branch : branchList) {
			final List<CubeDetail> detailList = branch.getDetailList();
			for (CubeDetail detail : detailList) {				
				final BigDecimal rate = detail.getRate();				
				final BigDecimal divRate = dividend.divide(rate, precision);				
				final String[] details = {baseCurrency, detail.getCurrency(), rate.toString(), divRate.toString(), dateTime};
				sb.append(System.lineSeparator());
				sb.append(StringUtils.arrayToCommaDelimitedString(details));
			}
		}

		logger.info("Done");
		return sb.toString();
	}

	@Override
	public void saveToFile(final String strFeed, final Path path) throws IOException {
		logger.info("Saving to file: {}", path.toString());
		FileUtils.writeStringToFile(formattedFeed, path);
		logger.info("Done");
	}

}
