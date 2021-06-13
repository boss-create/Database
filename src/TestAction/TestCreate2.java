package TestAction;

import java.util.ArrayList;
import java.util.List;

public class TestCreate2 {
    public static void main(String[] args){
        String sql = "create table student1(sno int primary key unique not null,sname char(10) not null,sdept char(30),ssex char(5),sage int);";
        sql = sql.toLowerCase().replaceAll("\\s{2,}", " ");
        sql = sql.trim();
        if(!sql.endsWith(";")){
//            throw new MyException("结尾缺少;");
        }
//        System.out.println("sql:"+sql);
        String create = sql.substring(0, sql.indexOf(" "));
        System.out.println("create:"+create);
        if("create"!=create){
//            throw new MyException("create拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);
        String table = sql.substring(0,sql.indexOf(" "));
        if("table"!=table){
//            throw new MyException("table拼写错误!");
        }
//        System.out.println("table:"+table);

        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0, sql.indexOf("("));
//        System.out.println("tableName:"+tableName);

        String fieldString = sql.substring(sql.indexOf("(")+1,sql.indexOf(";")).trim();
        fieldString = fieldString.substring(0,fieldString.length()-1);
//        System.out.println("fieldString:"+fieldString);
        String[] fieldEntry = fieldString.split(",");

        int len = fieldEntry.length;
        for(int i = 0; i < len; i++){
            fieldEntry[i] = fieldEntry[i].trim();
        }
        List<String[]> list = new ArrayList<>();
        for (String fields : fieldEntry) {
            String[] field = fields.split(" ");
            list.add(field);
        }
    }
}
