import cn.fish.nio.file.database.DataBase;
import cn.fish.nio.file.database.custom.UserDataRecord;
import cn.fish.nio.file.database.custom.UserRecordProcessor;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/16 16:19
 */
public class TestEntrance {

    public static void main(String[] args) throws Exception {
//        testAddData(1);
//        testReadAvg(2);
        testReadData(1);
//        testReadAvg(1);
    }

    public static void testAddData(int tableId) throws ParseException, IOException {
        DataBase<UserDataRecord> db = DataBase.getInstance(UserDataRecord.class, new UserRecordProcessor());
        Date date = DateUtils.parseDate("2018-01-10 19:00:02", "yyyy-MM-dd HH:mm:ss");
        long timeStamp = date.getTime();
        for (int i = 0; i < 1; i++) {
            UserDataRecord record = new UserDataRecord(timeStamp + i * 5 * 60 * 1000, 27, 150);
            db.addData(record, tableId);
        }
    }

    public static void testReadAvg(int tableId) throws Exception {
        DataBase<UserDataRecord> db = DataBase.getInstance(UserDataRecord.class, new UserRecordProcessor());
        UserDataRecord avg = db.getAvg(tableId, null, null);
        System.out.println(JSON.toJSONString(avg));

    }

    public static void testReadData(int tableId) throws Exception {
        DataBase<UserDataRecord> db = DataBase.getInstance(UserDataRecord.class, new UserRecordProcessor());
        List<UserDataRecord> records = db.readRecords(tableId, null, null);
        for (UserDataRecord record : records) {
            System.out.println(JSON.toJSONString(record));
        }
    }

    public static void testReadData2(int tableId) throws Exception {
        DataBase<UserDataRecord> db = DataBase.getInstance(UserDataRecord.class, new UserRecordProcessor());
        List<UserDataRecord> records = db.readRecords(tableId, null, null, 100);
        for (UserDataRecord record : records) {
            System.out.println(JSON.toJSONString(record));
        }
    }
}
