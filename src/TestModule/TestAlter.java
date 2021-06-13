package TestModule;

public class TestAlter {
    public static void main(String[] args){
        String sql = "alter table student drop column columntest;";
        sql = sql.toLowerCase();
        sql = sql.replaceAll("[\\s]{1,}", " ");
        // sql=""+sql+" ENDOFSQL";
        if (sql.endsWith(";"))
            sql = sql.substring(0, sql.length() - 1);
        sql = sql.trim();

        String alter = sql.substring(0, sql.indexOf(" "));
        System.out.println("alter:"+alter);
//        if (!"alter".equals(alter)) {
//            // alter拼写错误，抛出异常
//            throw new MyException("alter拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String table = sql.substring(0, sql.indexOf(" "));
        System.out.println("table:"+table);
//        if (!"table".equals(table)) {
//            // table拼写错误，抛出异常
//            throw new MyException("table拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String tableName = sql.substring(0, sql.indexOf(" ")).trim();
        System.out.println("tableName:"+tableName);
        sql = sql.substring(sql.indexOf(" ") + 1);
        String type = sql.substring(0, sql.indexOf(" ")).trim();
        System.out.println("type:"+type);
        sql = sql.substring(sql.indexOf(" ") + 1);
        System.out.println("sql:"+sql);
    }
}
