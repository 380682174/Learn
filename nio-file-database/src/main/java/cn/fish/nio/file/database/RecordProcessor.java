package cn.fish.nio.file.database;

import java.io.File;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/15 18:01
 */
public interface RecordProcessor<T extends Record> {

    /***
     * 获取平均值
     * @param records
     * @return
     */
    T getAvgRecord(List<T> records);

    /**
     * 获取数据文件存放的目录
     *
     * @return
     */
    File getDataDir();

}
