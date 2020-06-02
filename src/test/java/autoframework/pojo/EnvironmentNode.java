package autoframework.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnvironmentNode {
    private String environmentName;
    private String baseURL;
}
