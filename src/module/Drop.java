package module;

import java.io.File;

import SqlExecute.DBMS;
import SqlExecute.SqlException;

public class Drop {

    private String tableName;

    public Drop(String tableName) {
        this.tableName = tableName;
    }

    public String executeSQL() throws SqlException {
        File file = new File(DBMS.DATA_PATH + tableName + ".dbf");
        File file1 = new File(DBMS.DATA_PATH+tableName+"Constraint.dbf");
        if (!file.exists()) {
            // 表不存在，抛出异常
            throw new SqlException("表不存在");
        }
        file.delete();
        file1.delete();
        return "删除成功";
    }
}
