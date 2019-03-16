package cn.fish.nio.file.database.custom;

import cn.fish.nio.file.database.Record;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;

import java.nio.ByteBuffer;

public class UserDataRecord extends Record {

    @Getter
    @JSONField(name = "age")
    private int age;

    @Getter
    @JSONField(name = "weight")
    private int weight;

    public UserDataRecord() {
        super();
    }

    public UserDataRecord(int age, int weight) {
        super();
        this.timeStamp = System.currentTimeMillis();
        this.age = age;
        this.weight = weight;

    }

    public UserDataRecord(long timeStamp, int age, int weight) {
        super();
        this.timeStamp = timeStamp;
        this.age = age;
        this.weight = weight;

    }

    public UserDataRecord(ByteBuffer buffer) {
        super(buffer);
    }

    public UserDataRecord(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected void initData() {
        this.age = 0;
        this.weight = 0;
    }

    @Override
    protected void initSelfData(ByteBuffer buffer) {
        this.age = buffer.getInt();
        this.weight = buffer.getInt();

    }

    @Override
    public void putSelfDataToBuffer(ByteBuffer buffer) {
        buffer.putInt(this.age);
        buffer.putInt(this.weight);

    }
}