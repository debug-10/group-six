package top.wxy.framework.common.utils;

import cn.hutool.core.io.IoUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 笼中雀
 */
public class ExceptionUtils {

    /**
     * 获取异常信息
     *
     * @param e 异常
     * @return 返回异常信息
     */
    public static String getExceptionMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);

        // 关闭IO流
        IoUtil.close(pw);
        IoUtil.close(sw);

        return sw.toString();
    }
}