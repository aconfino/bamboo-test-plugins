package helloworld;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.opensymphony.xwork.TextProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

public class GitLogTaskConfigurator extends AbstractTaskConfigurator{
    private TextProvider textProvider;

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition){
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
        config.put("path", params.getString("path"));
        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context){
        super.populateContextForCreate(context);
        context.put("path", "C:/workspace/bamboo-task");
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition){
        super.populateContextForEdit(context, taskDefinition);
        context.put("path", taskDefinition.getConfiguration().get("path"));
    }

    @Override
    public void populateContextForView(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition){
        super.populateContextForView(context, taskDefinition);
        context.put("path", taskDefinition.getConfiguration().get("path"));
    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection){
        super.validate(params, errorCollection);
        final String projectPath = params.getString("path");
        if (validProjectPath(projectPath)){
            errorCollection.addError("path", textProvider.getText("helloworld.path.error"));
        }
    }
    
    public static boolean validProjectPath(String projectPath){
    	if (StringUtils.isEmpty(projectPath) || (!(new File(projectPath).exists()))){
    		return false;
    	}
    	return true;
    }

    public void setTextProvider(final TextProvider textProvider) {
        this.textProvider = textProvider;
    }
}
