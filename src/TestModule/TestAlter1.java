package TestModule;

public class TestAlter1 {
    public static void main(String[] args){
        //alter table student add columnTest int;
        String sql = " alter  table  student  drop  column   columnTest ;";
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");

        if(!sql.endsWith(";")){

        }

        String alter = sql.substring(0,sql.indexOf(" "));
        System.out.println("alter:"+alter);
        if(!alter.equals(alter)){

        }
        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);
        String table = sql.substring(0,sql.indexOf(" "));
        System.out.println("table:"+table);
        if(!table.equals("table")){

        }
        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0,sql.indexOf(" "));

        sql = sql.substring(sql.indexOf(" ")+1);
        String type = sql.substring(0,sql.indexOf(" "));
        System.out.println("sql:"+sql);
        System.out.println("type:"+type);

        sql = sql.substring(sql.indexOf(" ")+1,sql.length()-1).trim();

        System.out.println("sql:"+sql);
    }
}
