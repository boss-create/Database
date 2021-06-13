package TestModule;

public class TestUpdate {
    public static void main(String[] args){
        String sql = "update  student  set  sdept= 'WL' ,ssex = '女'  where  sno = 2016 ;";
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("[\\s]{2,}", " ");
        // sql=""+sql+" ENDOFSQL";
        if (sql.endsWith(";"))
            sql = sql.substring(0, sql.length() - 1);
        sql = sql.trim();

        String update = sql.substring(0, sql.indexOf(" "));
        System.out.println("update:"+update);
//        if (!"update".equals(update)) {
//            // update拼写错误，抛出异常
//            throw new MyException("update拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String tableName = sql.substring(0, sql.indexOf(" "));
        System.out.println("tableName:"+tableName);
        sql = sql.substring(sql.indexOf(" ") + 1);
        String set = sql.substring(0, sql.indexOf(" ")).trim();
        System.out.println(set);
//        if (!"set".equals(set)) {
//            // set拼写错误，抛出异常
//            throw new MyException("set拼写错误");
//        }
        sql = sql.substring(sql.indexOf(" ") + 1);
//        if (!sql.contains("where")) {
//            // 没有where限制条件，不符合update，抛出异常
//            throw new MyException("Update没有Where语句限制条件");
//        }
        String updateString = sql.substring(0, sql.indexOf("Condition")).trim();
        System.out.println("updateString:"+updateString);

        String whereString = sql.substring(sql.indexOf("Condition") + 5).trim();
        System.out.println("whereString:"+whereString);
    }
}
