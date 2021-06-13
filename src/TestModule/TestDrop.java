package TestModule;

public class TestDrop {
    public static void main(String[] args){
        String sql = "drop table test888;";
        sql = sql.toLowerCase();
        sql = sql.replaceAll("[\\s]{1,}", " ");
        // sql=""+sql+" ENDOFSQL";
        if (sql.endsWith(";"))
            sql = sql.substring(0, sql.length() - 1);
        sql = sql.trim();

        String drop = sql.substring(0, sql.indexOf(" "));
        System.out.println("drop:"+drop);
//        if (!"drop".equals(drop)) {
//            // drop拼写错误，抛出异常
//            throw new MyException("drop拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String table = sql.substring(0, sql.indexOf(" "));
        System.out.println("table:"+table);
//        if (!"table".equals(table)) {
//            // table拼写错误，抛出异常
//            throw new MyException("table拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String tableName = sql.trim();
        System.out.println("tableName:"+tableName);
    }
}
