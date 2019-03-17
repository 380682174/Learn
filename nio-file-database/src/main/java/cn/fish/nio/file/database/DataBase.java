package cn.fish.nio.file.database;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/16 15:41
 */
public class DataBase<T extends Record> {
    private final Map<Integer, DataTable<T>> tables = new HashMap<>();

    private final Class<T> recordClazz;

    private final RecordProcessor<T> recordProcessor;

    protected DataBase(Class<T> recordClazz, RecordProcessor<T> recordProcessor){
        this.recordClazz = recordClazz;
        this.recordProcessor = recordProcessor;
    }

    protected static final Map<String, DataBase> INSTANCES = new HashMap<>();

    /***
     * 获取数据库实例
     * @param clazz
     * @param recordProcessor
     * @param <T1>
     * @return
     */
    public static <T1 extends Record> DataBase getInstance(Class<T1> clazz, RecordProcessor<T1> recordProcessor) {
        if(INSTANCES.get(clazz.getName()) == null) {
            synchronized (DataBase.class) {
                if(INSTANCES.get(clazz.getName()) == null) {
                    INSTANCES.put(clazz.getName(), new DataBase<T1>(clazz, recordProcessor));
                }
            }
        }
        return INSTANCES.get(clazz.getName());
    }

    /**
     * 添加数据
     * @param record
     * @param tableId
     * @throws IOException
     */
    public void addData(T record, int tableId) throws IOException {
        if(!tables.containsKey(tableId)) {
            synchronized (this.getClass()) {
                if(!tables.containsKey(tableId)) {
                    DataTable<T> table = new DataTable<>(this.getTableFilePath(tableId), recordClazz);
                    tables.put(tableId, table);
                }
            }
        }
        tables.get(tableId).appendData(record);
    }

    /****
     * 获取表格数据
     * @param tableId
     * @return
     * @throws Exception
     */
    private DataTable<T> getTableForRead(int tableId) throws Exception {
        if(!tables.containsKey(tableId)) {
            synchronized (this.getClass()) {
                if(!tables.containsKey(tableId)) {
                    tables.put(tableId, new DataTable<>(this.getTableFilePath(tableId), recordClazz));
                }
            }
        }
        return tables.get(tableId);
    }

    /**
     * 根据时间查数据
     * @param tableId
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     * @throws Exception
     */
    public List<T> readRecords(int tableId, Long startTimeStamp, Long endTimeStamp) throws Exception {
        DataTable<T> table = this.getTableForRead(tableId);
        return table.readRecords(startTimeStamp, endTimeStamp);
    }

    /**
     * 根据参数读数据
     * maxRecordNum如果小于总记录数，打散取点
     * @param tableId
     * @param startTimeStamp
     * @param endTimeStamp
     * @param maxRecordNum
     * @return
     * @throws Exception
     */
    public List<T> readRecords(int tableId, Long startTimeStamp, Long endTimeStamp, int maxRecordNum) throws Exception {
        DataTable<T> table = this.getTableForRead(tableId);
        return table.readRecords(startTimeStamp, endTimeStamp, maxRecordNum);
    }

    /***
     * 获取平均值
     * @param tableId
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     * @throws Exception
     */
    public T getAvg(int tableId, Long startTimeStamp, Long endTimeStamp) throws Exception {
        return this.getTableForRead(tableId).getAvg(startTimeStamp, endTimeStamp, this.recordProcessor);
    }

    /***
     * 删除表格
     * @param tableIds
     * @throws IOException
     */
    public void delete(int... tableIds) throws IOException {
        for(int tableId : tableIds) {
            DataTable table = tables.get(tableId);
            if(table != null) {
                table.close();
                FileUtils.forceDelete(table.getFile());
                tables.remove(tableId);
            }
        }
    }

    /**
     * 获取表格数据文件
     * @param tableId
     * @return
     * @throws IOException
     */
    private String getTableFilePath(int tableId) throws IOException {
        File dir = this.recordProcessor.getDataDir();
        if(!dir.exists()) {
            Files.createDirectories(dir.toPath());
        }
        return dir.getAbsolutePath() + File.separator + tableId + ".dat";
    }

}
