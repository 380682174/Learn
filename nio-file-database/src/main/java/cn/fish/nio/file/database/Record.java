package cn.fish.nio.file.database;


import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/15 17:57
 */
public abstract class Record {

    @Getter
    protected long timeStamp;

    public String getTime() {
        return DateFormatUtils.format(new Date(timeStamp), "yyyy-MM-dd HH:ss:mm");
    }

    protected Record() {
    }

    protected Record(ByteBuffer byteBuffer) {

        this.initData(byteBuffer);

    }

    public Record(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        this.initData(buffer);
    }

    /**
     * 子类初始化自己的默认数据
     */
    protected abstract void initData();

    private void initData(ByteBuffer byteBuffer) {

        this.timeStamp = byteBuffer.getLong();
        this.initSelfData(byteBuffer);

    }

    /**
     * 初始化自己的数据，从buffer中取出
     *
     * @param byteBuffer
     */
    protected abstract void initSelfData(ByteBuffer byteBuffer);

    /**
     * 将数据记录放入buffer
     *
     * @return
     */
    public final ByteBuffer toByteBuffer() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(Record.computeRecordSize(this.getClass()));
        byteBuffer.putLong(timeStamp);
        this.putSelfDataToBuffer(byteBuffer);
        byteBuffer.flip();
        return byteBuffer;

    }

    /***
     * 子类将自己的数据放入buffer
     * @param byteBuffer
     */
    protected abstract void putSelfDataToBuffer(ByteBuffer byteBuffer);

    public static int computeRecordSize(Class<? extends Record> clazz) {

        //默认字段timeStamp的长度
        int size = Long.SIZE;
        for (Field field : clazz.getDeclaredFields()) {

            String fieldType = field.getType().getName();
            if ("byte".equals(fieldType) || fieldType.equals(Byte.class.getName())) {
                size += Byte.SIZE;
            } else if ("short".equals(fieldType) || fieldType.equals(Short.class.getName())) {
                size += Short.SIZE;
            } else if ("int".equals(fieldType) || fieldType.equals(Integer.class.getName())) {
                size += Integer.SIZE;
            } else if ("long".equals(fieldType) || fieldType.equals(Long.class.getName())) {
                size += Long.SIZE;
            } else if ("float".equals(fieldType) || fieldType.equals(Float.class.getName())) {
                size += Float.SIZE;
            } else if ("double".equals(fieldType) || fieldType.equals(Double.class.getName())) {
                size += Double.SIZE;
            }

        }

        //表示这种实体类的一条记录固定占用的字节个数
        return size / Byte.SIZE;

    }

}
