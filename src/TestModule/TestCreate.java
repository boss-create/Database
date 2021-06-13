package TestModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestCreate {
    public static void main(String[] args){
        String sql = "  create   table students (id int primary key unique not null, name char(10)   not  null, age int) ;";
        sql = sql.toLowerCase().replaceAll("\\s{2,}", " ");
        sql = sql.trim();

        System.out.println("sql:"+sql);
        String create = sql.substring(0, sql.indexOf(" "));
        System.out.println("create:"+create);
        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);
        String table = sql.substring(0,sql.indexOf(" "));
        System.out.println("table:"+table);

        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0, sql.indexOf("("));
        System.out.println("tableName:"+tableName);

        String fieldString = sql.substring(sql.indexOf("(")+1,sql.indexOf(";")).trim();
        fieldString = fieldString.substring(0,fieldString.length()-1);
        System.out.println("fieldString:"+fieldString);
        String[] fieldEntry = fieldString.split(",");
        for(String str:fieldEntry)
        System.out.println("fieldEntry:"+str);
        List<String[]>fieldList = new ArrayList<>();
        for(String fields:fieldEntry){
            String[] field = fields.split(" ");
            fieldList.add(field);
        }
        for(String[] str:fieldList){
            for(String str1:str){
                System.out.println(str1);
            }
        }
    }
}
