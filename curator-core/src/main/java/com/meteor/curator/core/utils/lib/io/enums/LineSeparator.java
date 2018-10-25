package com.meteor.curator.core.utils.lib.io.enums;

/**
 * created by Meteor on 2018/10/21
 * 换行符枚举<br>
 * 换行符包括：
 * <pre>
 * Mac系统换行符："\r"
 * Linux系统换行符："\n"
 * Windows系统换行符："\r\n"
 * </pre>
 *
 * @see #MAC
 * @see #LINUX
 * @see #WINDOWS
 */
public enum LineSeparator {
    /** Mac系统换行符："\r" */
    MAC("\r"),
    /** Linux系统换行符："\n" */
    LINUX("\n"),
    /** Windows系统换行符："\r\n" */
    WINDOWS("\r\n");

    private String value;

    private LineSeparator(String lineSeparator) {
        this.value = lineSeparator;
    }

    public String getValue() {
        return this.value;
    }
}
