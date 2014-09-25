package helloworld;

import static org.junit.Assert.*;

import org.junit.Test;

public class GitLogTaskConfiguratorTest {
	
	@Test
	public void validProjectPathTest(){
		assertFalse(GitLogTaskConfigurator.validProjectPath(null));
		assertFalse(GitLogTaskConfigurator.validProjectPath("c:/does/not/exist"));
		assertTrue(GitLogTaskConfigurator.validProjectPath("c:/workspace/bamboo-task/user-input"));
	}

}
