package autoframework.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusFlag {
    private boolean needToResetApp;
    private boolean needToRestartApp;
    private boolean isAtStartPage;
    private boolean isLogin;
    private String currentUsername;

    private boolean haveSkippedStartTipAtHomePage;
}
