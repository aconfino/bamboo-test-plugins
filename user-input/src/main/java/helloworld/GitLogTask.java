package helloworld;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;


public class GitLogTask implements TaskType{
	private final ProcessService processService;
	 
    public GitLogTask(final ProcessService processService) {
        this.processService = processService;
    }
    
    @NotNull
    @java.lang.Override
    public TaskResult execute(@NotNull final TaskContext taskContext) throws TaskException{
    	  TaskResultBuilder builder = TaskResultBuilder.create(taskContext);
          
    	  final String projectPath = taskContext.getConfigurationMap().get("path");
    	  
          ExternalProcess process = processService.createProcess(taskContext, 
                  new ExternalProcessBuilder()
                  .command(Arrays.asList("git", "--git-dir", projectPath + "/.git", "log"))
                  .workingDirectory(taskContext.getWorkingDirectory()));
   
          process.execute();
           
          return builder.checkReturnCode(process, 0).build();
    }
}
