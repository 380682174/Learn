package cn.fish.nio.file.database.custom;

import cn.fish.nio.file.database.RecordProcessor;
import cn.fish.nio.file.database.common.Constant;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/16 15:58
 */
public class UserRecordProcessor implements RecordProcessor<UserDataRecord> {
    @Override
    public UserDataRecord getAvgRecord(List<UserDataRecord> records) {
        Validate.notNull(records, "records cannot be null");
        long ageSum = 0L;
        int ageCount = 0;
        long weightSum = 0L;
        int weightCount = 0;

        for (UserDataRecord record : records) {
            if (record.getAge() >= 0) {
                ageSum += record.getAge();
                ageCount++;
            }
            if (record.getWeight() >= 0) {
                weightSum += record.getWeight();
                weightCount++;
            }

        }
        return new UserDataRecord(
                ageCount != 0 ? (int) (ageSum / ageCount) : -1,
                weightCount != 0 ? (int) (weightSum / weightCount) : -1
        );
    }

    @Override
    public File getDataDir() {

        return new File(Constant.WORK_DIR, "userData" + File.separator + "user");

    }

}
