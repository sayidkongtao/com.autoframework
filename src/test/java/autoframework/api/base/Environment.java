package autoframework.api.base;

import autoframework.pojo.EnvironmentInfo;
import autoframework.pojo.EnvironmentNode;
import autoframework.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Environment {
    private Logger logger = LogManager.getLogger(this.getClass());
    private EnvironmentNode environmentNode;

    public Environment() {
        getDefaultEnvironment();
    }

    public EnvironmentNode getEnvironmentNode() {
        return environmentNode;
    }

    private void getDefaultEnvironment() {
        EnvironmentInfo environmentInfo = Utils.deserialize("api-environment/environment.json", EnvironmentInfo.class);
        String defaultName = environmentInfo.getDefaultEnvironment();
        logger.info("To get environment by: " + defaultName);

        EnvironmentNode environmentNode = environmentInfo.getEnvironmentList().stream().filter(env -> StringUtils.endsWithIgnoreCase(env.getEnvironmentName(), defaultName)).findFirst().get();
        this.environmentNode = environmentNode;
        logger.info("The api default name is: " + defaultName + ", the base url is " + this.environmentNode.getBaseURL());
    }
}
