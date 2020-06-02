package autoframework.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EnvironmentInfo {
    private String defaultEnvironment;
    private List<EnvironmentNode> environmentList;
}
