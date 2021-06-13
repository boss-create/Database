package TestModule;

public class TestDelete {
    public static void main(String[] args){
        String sql = "delete from student  where  sage = 19 ;";
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
        System.out.println("sql:"+sql);

        if(sql.endsWith(";")){
            sql = sql.substring(0,sql.length()-1).trim();
        }
        String delete = sql.substring(0, sql.indexOf(" "));
        System.out.println("delete:"+delete);
        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);

        String from = sql.substring(0, sql.indexOf(" "));
        System.out.println("from:"+from);

        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);

        String tableName = sql.substring(0,sql.indexOf(" "));
        System.out.println("tableName:"+tableName);
        String whereString = null;
        if(sql.contains("Condition")) {
            sql = sql.substring(sql.indexOf(" ") + 1);
            System.out.println("sql:" + sql);

            String where = sql.substring(0, sql.indexOf(" "));
            System.out.println("where:" + where);

            sql = sql.substring(sql.indexOf(" ") + 1);
            System.out.println("sql:" + sql);

            whereString = sql.trim().replaceAll("\\s+", "");
            System.out.println("whereString:" + whereString);
        }
    }
}
