package helloworld;

import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.utils.process.ExternalProcess;

import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.process.ExternalProcessBuilder;

import java.util.Arrays;

public class JavaTaskType implements TaskType {
    private final ProcessService processService;
 
    public JavaTaskType(final ProcessService processService) {
        this.processService = processService;
    }
     
    public TaskResult execute(final TaskContext taskContext) throws TaskException {
        TaskResultBuilder builder = TaskResultBuilder.create(taskContext);
         
        ExternalProcess process = processService.createProcess(taskContext, 
                new ExternalProcessBuilder()
                .command(Arrays.asList("C:\\PROGRA~1\\Java\\jdk1.7.0_15\\bin\\java", "-version"))
                .workingDirectory(taskContext.getWorkingDirectory()));
 
        process.execute();
         
        return builder.checkReturnCode(process, 0).build();
    }
}