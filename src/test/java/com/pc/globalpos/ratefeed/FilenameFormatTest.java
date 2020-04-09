package com.pc.globalpos.ratefeed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;
import com.pc.globalpos.ratefeed.source.EcbImpl;

/**
 * @author gino.q
 * @date April 8, 2020
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class FilenameFormatTest {
	
	@Autowired
	ApplicationProperties props;
	
	@MockBean
	private EcbImpl ecbImpl;

	@Test
	public void test() {
        System.out.println("Filename: "+props.getFilename());        
	}
}
