package top.wxy.storage.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import java.io.InputStream;
import java.util.Date;

/**
 * @author 笼中雀
 */
public abstract class StorageService {
    /**
     * 根据⽂件名，⽣成带时间戳的新⽂件名
     *
     * @param fileName ⽂件名
     * @return 返回带时间戳的⽂件名
     */
    public String getNewFileName(String fileName) {
        // 主⽂件名，不包含扩展名
        String prefix = FileNameUtil.getPrefix(fileName);
        // ⽂件扩展名
        String suffix = FileNameUtil.getSuffix(fileName);
        // 把当天HH:mm:ss，转换成秒
        long time = DateUtil.timeToSecond(DateUtil.formatTime(new Date()))
                ;
        // 新⽂件名
        return prefix + "_" + time + "." + suffix;
    }
    /**
     * ⽣成路径，不包含⽂件名
     *
     * @return 返回⽣成的路径
     */
    public String getPath() {
        // ⽂件路径
        return DateUtil.format(new Date(), "yyyyMMdd");
    }
/**
 * 根据⽂件名，⽣成路径
 *
 * @param fileName ⽂件名
 * @return ⽣成⽂件路径
 */
public String getPath(String fileName) {
    return getPath() + "/" + getNewFileName(fileName);
}
    /**
     * ⽂件上传
     *
     * @param data ⽂件字节数组
     * @param path ⽂件路径，包含⽂件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);
    /**
     * ⽂件上传
     *
     * @param inputStream 字节流
     * @param path ⽂件路径，包含⽂件名
     * @return 返回http地址
     */
    abstract String upload(InputStream inputStream, String path);
}
