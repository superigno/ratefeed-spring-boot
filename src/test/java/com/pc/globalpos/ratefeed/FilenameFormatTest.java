package com.pc.globalpos.ratefeed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.globalpos.ratefeed.model.ApplicationProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilenameFormatTest {
	
	@Autowired
	ApplicationProperties props;

	@Test
	public void test() {
        System.out.println("Filename: "+props.getFilename());        
	}
}
