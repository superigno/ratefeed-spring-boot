package com.pc.globalpos.ratefeed.source;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pc.globalpos.ratefeed.core.XmlConverter;
import com.pc.globalpos.ratefeed.model.ecb.CubeBranch;
import com.pc.globalpos.ratefeed.model.ecb.CubeDetail;
import com.pc.globalpos.ratefeed.model.ecb.CubeRoot;
import com.pc.globalpos.ratefeed.model.ecb.Envelope;
import com.pc.globalpos.ratefeed.util.FileUtils;

@Component
public class EcbImpl implements RateSource {
	
	//private static final Logger logger = LoggerFactory.getLogger(RateSource.class);
	
	@Autowired
	private XmlConverter converter;

	@Override
	public Object getFeed(URL url) throws IOException {
		Envelope envelope = (Envelope) converter.convertFromXmlToObject(url);
		return envelope;
	}

	@Override
	public Object getFeed(Path path) throws IOException {		
		Envelope envelope = (Envelope) converter.convertFromXmlToObject(path.toString());		
		return envelope;
	}
	
	@Override
	public void saveFeed(Object feed, Path path) throws IOException {
		Envelope envelope = (Envelope) feed;
		CubeRoot root = (CubeRoot) envelope.getCubeRoot();
		List<CubeBranch> branchList = root.getCubeBranchList();
		StringBuilder sb = new StringBuilder();

		for (CubeBranch branch : branchList) {
			List<CubeDetail> detailList = branch.getDetailList();
			for (CubeDetail detail : detailList) {				
				String s = detail.getCurrency() + "," + detail.getRate();
				sb.append(s);
				sb.append(System.lineSeparator());
			}
		}
		
		FileUtils.writeStringToFile(sb.toString(), path);
	}

}
