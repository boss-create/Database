package TestModule;

import java.util.Locale;

public class TestDrop1 {
    public static void main(String[] args){
        String sql = "drop   table   test888  ; ";
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
        System.out.println("sql:"+sql);

        if(!sql.endsWith(";")){

        }
        String drop = sql.substring(0,sql.indexOf(" "));
        System.out.println("drop:"+drop);

        sql = sql.substring(sql.indexOf(" ")+1);
        String table = sql.substring(0,sql.indexOf(" "));
        System.out.println("table:"+table);

        String tableName = sql.substring(sql.indexOf(" ")+1,sql.length()-1).trim();
        System.out.println("tableName:"+tableName);

    }
}
