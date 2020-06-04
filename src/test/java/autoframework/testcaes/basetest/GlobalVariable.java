package autoframework.testcaes.basetest;

import autoframework.pojo.StatusFlag;

public class GlobalVariable {
    public final static StatusFlag STATUSFLAG = new StatusFlag();

    public static void init() {
        STATUSFLAG.setNeedToResetApp(false);
        STATUSFLAG.setNeedToRestartApp(false);
        STATUSFLAG.setAtStartPage(true);
        STATUSFLAG.setLogin(false);
        STATUSFLAG.setHaveSkippedStartTipAtHomePage(false);
    }

    public static void localDebug() {
        STATUSFLAG.setNeedToResetApp(false);
        STATUSFLAG.setNeedToRestartApp(false);
        STATUSFLAG.setAtStartPage(false);
        STATUSFLAG.setLogin(true);
        STATUSFLAG.setHaveSkippedStartTipAtHomePage(true);
    }
}
