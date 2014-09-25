package helloworld;

import static org.junit.Assert.*;

import org.junit.Test;

public class GitLogTaskConfiguratorTest {
	
	@Test
	public void validProjectPathTest(){
		assertFalse(GitLogTaskConfigurator.validGitProject(null));
		assertFalse(GitLogTaskConfigurator.validGitProject("c:/does/not/exist"));
		assertTrue(GitLogTaskConfigurator.validGitProject("c:/workspace/bamboo-task/user-input"));
	}

}
