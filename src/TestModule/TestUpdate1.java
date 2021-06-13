package TestModule;

public class TestUpdate1 {
    public static void main(String[] args){
        String sql = "Update  student  set  sdept= 'WL' ,   ssex = '女'  ;";
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
        System.out.println("sql:"+sql);
        if(!sql.endsWith(";")){

        }
        String update = sql.substring(0,sql.indexOf(" "));
        System.out.println("update:"+update);

        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0,sql.indexOf(" "));
        System.out.println("tableName:"+tableName);

        sql = sql.substring(sql.indexOf(" ")+1);
        String set = sql.substring(0,sql.indexOf(" "));
        System.out.println("set:"+set);

        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);

        String updateString = null;
        String whereString = null;
        if(sql.contains("Condition")){
            updateString = sql.substring(0,sql.indexOf("Condition")).replaceAll("\\s+","");
            System.out.println("updateString:"+updateString);
            sql = sql.substring((sql.indexOf("Condition")));
            System.out.println("sql:"+sql);

            String where = sql.substring(sql.indexOf("Condition"),sql.indexOf(" "));
            System.out.println("where:"+where);
            if(!where.equals("Condition")){

            }
            whereString = sql.substring(sql.indexOf(" ")+1,sql.indexOf(";")).replaceAll("\\s+","");
            System.out.println("whereString:"+whereString);
        }else{
            updateString = sql.substring(0,sql.length()-1).replaceAll("\\s+","");
            System.out.println("updateString:"+updateString);
        }

    }
}
