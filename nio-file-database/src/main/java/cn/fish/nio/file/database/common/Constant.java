package cn.fish.nio.file.database.common;

import java.io.File;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/3/16 16:03
 */
public class Constant {

    public static final File WORK_DIR = new File(System.getProperty("user.dir"), "work_dir") {
        private static final long serialVersionUID = 1L;
        {
            if (!this.exists()) {
                this.mkdirs();
            }

        }
    };

}
