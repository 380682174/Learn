package cn.fish.nio.file.database;

import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.*;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/15 18:04
 */
public class DataTable<T extends Record> {

    /**
     * 数据文件
     */
    @Getter
    File file;
    /**
     * 具体类
     */
    @Getter
    Class<T> recordClazz;
    /**
     * 一条记录所占的字节大小
     */
    @Getter
    int recordSize;

    RandomAccessFile randomAccessFile;

    /**
     * FileChannel是一个用读写，映射和操作一个文件的通道。除了读写操作之外，
     * 还有裁剪特定大小文件truncate()，
     * 强制在内存中的数据刷新到硬盘中去force()，
     * 对通道上锁lock()等功能。
     */
    FileChannel fileChannel;

    private static final Set<String> SUPPORTED_FIELD_TYPES = new HashSet<String>();

    static {
        SUPPORTED_FIELD_TYPES.add("byte");
        SUPPORTED_FIELD_TYPES.add(Byte.class.getName());
        SUPPORTED_FIELD_TYPES.add("short");
        SUPPORTED_FIELD_TYPES.add(Short.class.getName());
        SUPPORTED_FIELD_TYPES.add("int");
        SUPPORTED_FIELD_TYPES.add(Integer.class.getName());
        SUPPORTED_FIELD_TYPES.add("long");
        SUPPORTED_FIELD_TYPES.add(Long.class.getName());
        SUPPORTED_FIELD_TYPES.add("float");
        SUPPORTED_FIELD_TYPES.add(Float.class.getName());
        SUPPORTED_FIELD_TYPES.add("double");
        SUPPORTED_FIELD_TYPES.add(Double.class.getName());
    }

    public DataTable(String tableFilePath, Class<T> recordClazz) throws FileNotFoundException {
        this.file = new File(tableFilePath);
        this.recordClazz = recordClazz;
        this.recordSize = Record.computeRecordSize(recordClazz);
        this.randomAccessFile = new RandomAccessFile(this.file, "rw");
        this.fileChannel = randomAccessFile.getChannel();
    }

    public void appendData(T record) throws IOException {

        /***
         * 修改数据，保证通道独有
         */
        try (FileLock fileLock = fileChannel.lock()) {
            //returns the file size of the file the channel is connected to.
            long channelSize = fileChannel.size();
            //如果文件里已经有数据
            if (channelSize >= this.recordSize) {

                ByteBuffer byteBuffer = ByteBuffer.allocate(this.recordSize);
                //将channel内容放入buffer
                long position = channelSize - this.recordSize;
                fileChannel.read(byteBuffer, position);
                byteBuffer.flip();
                if (byteBuffer.getLong() >= record.getTimeStamp()) {
                    //插入时间戳为中间的数据
                    int recordNo = this.getMinRecordNoByTimeStampGte(record.getTimeStamp(), 0, (int) (channelSize / this.recordSize - 1));
                    //去除时间比当前传入的时间戳大的数据
                    fileChannel.truncate(recordNo * this.recordSize);
                }
            }
            ByteBuffer byteBuffer1 = record.toByteBuffer();
            fileChannel.write(byteBuffer1, channelSize);
            fileChannel.force(false);

        } catch (OverlappingFileLockException e) {

            throw new RuntimeException("系统正忙，请稍后再试！");

        }

    }

    public ByteBuffer readDataAsByteBuffer(Long startTimeStamp, Long endTimeStamp) throws Exception {
        if (startTimeStamp != null && endTimeStamp != null && startTimeStamp > endTimeStamp) {
            throw new Exception("开始时间不能大于结束时间");
        }
        try (FileLock fileLock = fileChannel.lock()) {
            int recordNum = (int) (fileChannel.size() / recordSize);
            int startRecordNo = startTimeStamp != null ? getMinRecordNoByTimeStampGte(startTimeStamp, 0, recordNum - 1) : 0;
            if (startRecordNo != -1) {
                if (endTimeStamp != null) {
                    int endRecordNo = getMaxRecordNoByTimeStampLt(endTimeStamp, startRecordNo, recordNum - 1);
                    if (startRecordNo > endRecordNo) {
                        return null;
                    }
                    int offset = startRecordNo * recordSize;
                    int length = endRecordNo * recordSize + recordSize - offset;
                    ByteBuffer buffer = ByteBuffer.allocate(length);
                    fileChannel.read(buffer, offset);
                    return (ByteBuffer) buffer.flip();
                } else {
                    ByteBuffer byteBuffer = ByteBuffer.allocate((recordNum - startRecordNo) * recordSize);
                    fileChannel.read(byteBuffer, startRecordNo * recordSize);
                    return (ByteBuffer) byteBuffer.flip();
                }
            } else {
                return ByteBuffer.allocate(0);
            }
        } catch (OverlappingFileLockException e) {
            throw new RuntimeException("系统正忙，请稍后再试！");
        }
    }

    public List<T> readRecords(Long startTimeStamp, Long endTimeStamp) throws Exception {
        ByteBuffer buffer = this.readDataAsByteBuffer(startTimeStamp, endTimeStamp);
        if (buffer == null) {
            return Collections.emptyList();
        } else {
            List<T> records = new ArrayList<>(buffer.limit() / this.recordSize);
            byte[] bytes = new byte[this.recordSize];
            while (buffer.hasRemaining()) {
                buffer.get(bytes);
                records.add(this.recordClazz.getConstructor(byte[].class).newInstance(bytes));
            }
            return records;
        }
    }

    public ByteBuffer readDataAsByteBuffer(Long startTimeStamp, Long endTimeStamp, int maxRecordNum) throws Exception {
        if (startTimeStamp != null && endTimeStamp != null && startTimeStamp > endTimeStamp) {
            throw new Exception("开始时间不能大于结束时间");
        }
        try (FileLock fileLock = fileChannel.lock()) {
            int totalRecordNum = (int) (fileChannel.size() / this.recordSize);
            int startRecordNo = startTimeStamp != null ? getMinRecordNoByTimeStampGte(startTimeStamp, 0, totalRecordNum - 1) : 0;
            if (startRecordNo != -1) {
                int endRecordNo;
                if (endTimeStamp != null) {
                    endRecordNo = getMaxRecordNoByTimeStampLt(endTimeStamp, startRecordNo, totalRecordNum - 1);
                } else {
                    endRecordNo = (int) (fileChannel.size() / this.recordSize - 1);
                }
                if (startRecordNo > endRecordNo) {
                    return ByteBuffer.allocate(0);
                }
                int recordNum = endRecordNo - startRecordNo + 1;
                ByteBuffer buffer = ByteBuffer.allocate(Math.min(recordNum, maxRecordNum) * this.recordSize);
                ByteBuffer tmpBuffer = ByteBuffer.allocate(this.recordSize);
                double step = recordNum <= maxRecordNum ? 1 : recordNum / (double) maxRecordNum;
                for (int i = 0, currentRecordNo = startRecordNo; buffer.position() < buffer.capacity(); i++) {
                    int recordNo = startRecordNo + (int) Math.floor(step * i);
                    if (recordNo > currentRecordNo || i == 0) {
                        tmpBuffer.clear();
                        fileChannel.read(tmpBuffer, recordNo * this.recordSize);
                        tmpBuffer.flip();
                        buffer.put(tmpBuffer);
                    }
                    currentRecordNo = recordNo;
                }
                return (ByteBuffer) buffer.flip();
            }
            return ByteBuffer.allocate(0);
        } catch (OverlappingFileLockException e) {
            throw new RuntimeException("系统正忙，请稍后再试！");
        }
    }

    public List<T> readRecords(Long startTimeStamp, Long endTimeStamp, int maxRecordNum) throws Exception {
        ByteBuffer buffer = this.readDataAsByteBuffer(startTimeStamp, endTimeStamp, maxRecordNum);
        if (buffer == null) {
            return Collections.emptyList();
        } else {
            List<T> records = new ArrayList<>(buffer.limit() / this.recordSize);
            byte[] bytes = new byte[this.recordSize];
            while (buffer.hasRemaining()) {
                buffer.get(bytes);
                records.add(this.recordClazz.getConstructor(byte[].class).newInstance(bytes));
            }
            return records;
        }
    }

    public T getAvg(Long startTimeStamp, Long endTimeStamp, RecordProcessor<T> recordProcessor) throws Exception {
        ByteBuffer buffer = readDataAsByteBuffer(startTimeStamp, endTimeStamp);
        int len = buffer.limit() / this.recordSize;
        List<T> records = this.readRecords(startTimeStamp, endTimeStamp);
        return recordProcessor.getAvgRecord(records);
    }

    public int getMax(Long startTimeStamp, Long endTimeStamp) {
        return 0;
    }

    public void close() throws IOException {
        randomAccessFile.close();

    }

    /**
     * 获取时间戳大于等于传入对象时间戳的最小记录id
     * @param targetTimeStamp
     * @param startRecordNo
     * @param endRecordNo
     * @return
     * @throws IOException
     */
    private int getMinRecordNoByTimeStampGte(long targetTimeStamp, int startRecordNo, int endRecordNo) throws IOException {

        long maxRecordNo = endRecordNo;

        ByteBuffer byteBuffer = ByteBuffer.allocate(recordSize);
        //折半查询算法计算
        while (startRecordNo <= endRecordNo) {
            int mid = (startRecordNo + endRecordNo) / 2;
            byteBuffer.clear();
            fileChannel.read(byteBuffer, (long) mid * recordSize);
            byteBuffer.flip();
            long timeStamp = byteBuffer.getLong();
            if (timeStamp > targetTimeStamp) {
                endRecordNo = mid - 1;
            } else if (timeStamp < targetTimeStamp) {
                startRecordNo = mid + 1;
            } else {
                return mid;
            }
        }
        int recordNo = startRecordNo;
        if (recordNo <= maxRecordNo) {
            return recordNo;
        } else {
            return -1;
        }

    }

    /***
     * 获取时间戳小于传入对象时间戳的最大记录id
     * @param targetTimeStamp
     * @param startRecordNo
     * @param endRecordNo
     * @return
     * @throws IOException
     */
    private int getMaxRecordNoByTimeStampLt(long targetTimeStamp, int startRecordNo, int endRecordNo) throws IOException {
        long minOffset = startRecordNo;
        ByteBuffer byteBuffer = ByteBuffer.allocate(recordSize);
        while (startRecordNo <= endRecordNo) {
            int mid = (startRecordNo + endRecordNo) / 2;
            byteBuffer.clear();
            fileChannel.read(byteBuffer, (long) mid * recordSize);
            byteBuffer.flip();
            long timeStamp = byteBuffer.getLong();
            if (timeStamp > targetTimeStamp) {
                endRecordNo = mid - 1;
            } else if (timeStamp < targetTimeStamp) {
                startRecordNo = mid + 1;
            } else {
                endRecordNo = mid - 1;
                break;
            }
        }
        int recordNo = endRecordNo;
        if (recordNo >= minOffset) {
            return recordNo;
        } else {
            return -1;
        }
    }

}
