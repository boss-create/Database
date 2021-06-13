package TestModule;

public class TestInsert {
    public static void main(String[] args){
//        String sql = " insert  into  student  (sno, sname, sdept, ssex, sage)  values  (2016, 'jack4', 'IS4','男4',24) ; ";
        String sql = "insert into sc(sno,cno,grade) values(2013,01,88.5);";
        sql = sql.replaceAll("\\s+"," ").trim();
        System.out.println(sql);
        if(sql.endsWith(";")){
            sql = sql.substring(0,sql.length()-1);
        }

        String insert = sql.substring(0,sql.indexOf(" "));
        System.out.println(insert);
        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println(sql);
        String into = sql.substring(0,sql.indexOf(" "));
        System.out.println(into);
        sql = sql.substring(sql.indexOf(" ")+1);
        System.out.println("sql:"+sql);
        String tableName = sql.substring(0,sql.indexOf("("));
        tableName = tableName.trim();
        System.out.println("tableName:"+tableName);
        String field = sql.substring(sql.indexOf("(") + 1,
                sql.indexOf(")"));
        System.out.println("field:"+field);
        field = field.replaceAll("\\s","");
        System.out.println("field1:"+field);
        sql = sql.substring(sql.indexOf(")") + 1).trim();
        System.out.println("sql:"+sql);
        String values = sql.substring(0, sql.indexOf("(")).trim();
        System.out.println("values:"+values);
        sql = sql.substring(sql.indexOf(" ") + 1);
        System.out.println("sql:"+sql);
        String valueString = sql.substring(sql.indexOf("(") + 1,
                sql.indexOf(")"));
        System.out.println("valueString:"+valueString);
        valueString = valueString.replaceAll("\\s","");
        System.out.println("valueString1:"+valueString);

    }
}
