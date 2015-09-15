package helloworld;

import java.util.Arrays;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskState;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;

public class JavaTaskType implements TaskType {
    private final ProcessService processService;
 
    public JavaTaskType(final ProcessService processService) {
        this.processService = processService;
    }
     
    public TaskResult execute(final TaskContext taskContext) throws TaskException {
        TaskResultBuilder builder = TaskResultBuilder.create(taskContext);
        BuildLogger buildLogger = taskContext.getBuildLogger();
        
        String badJavaPath = "C:\\PROGRA~1\\Java\\jdk1.7.0_15\\bin\\java";
        String goodJavaPath = "C:\\PROGRA~1\\Java\\jdk1.8.0_45\\bin\\java";
        
        ExternalProcess successfulProcess = processService.createProcess(taskContext, 
                new ExternalProcessBuilder()
                .command(Arrays.asList(goodJavaPath, "-version"))
                .workingDirectory(taskContext.getWorkingDirectory()));
 
        successfulProcess.execute();  
        builder = builder.checkReturnCode(successfulProcess, 0);
        
        if(builder.getTaskState() == TaskState.ERROR){
        	buildLogger.addErrorLogEntry("Task state == error");
        	return builder.build();
        } else if(builder.getTaskState() == TaskState.FAILED){
        	buildLogger.addErrorLogEntry("Task state == failed");
        	return builder.build();
        } else {
        	buildLogger.addBuildLogEntry("Process succeeded.  Executing downstream processes");
        }
        
        ExternalProcess failingProcess = processService.createProcess(taskContext, 
                new ExternalProcessBuilder()
                .command(Arrays.asList(badJavaPath, "-version"))
                .workingDirectory(taskContext.getWorkingDirectory()));
 
        failingProcess.execute();  
        builder = builder.checkReturnCode(failingProcess, 0);

        return builder.build();
    }
}