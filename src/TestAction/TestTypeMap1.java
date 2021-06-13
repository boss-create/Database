package TestAction;

import SqlExecute.SqlException;
import com.linuxense.javadbf.DBFField;
import module.Create;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTypeMap1 {
    private List<Map<String, Object>> fields;// 一个表里所有的typeMap
    private String tableName;//表名
    private boolean isConstraint = false;//是否有约束条件

    public TestTypeMap1(List<String[]> fields, String tableName) throws SqlException {
        super();
        this.tableName = tableName;
        this.fields = new ArrayList<Map<String, Object>>();
        init(fields);
    }

    public static class Constraint {

        public static final int PRIMARY_KEY = 1;
        public static final int UNIQUE = 2;
        public static final int NOT_NULL = 4;
    }

    private void init(@NotNull List<String[]> fields) throws SqlException {  //fields 对应的是table 每个字段的属性数组(包括属性名)
        for (String[] field : fields) {
            Map<String, Object> typeMap = new HashMap<>();
            typeMap.put("fieldName", field[0]);
            byte type = 0;
            int length = 0;
            if (field[1].equals("int")) {
                type = DBFField.FIELD_TYPE_N;
                length = 10; //默认长度为10
            } else if (field[1].equals("double")) {
                type = DBFField.FIELD_TYPE_N;
                length = 10;
            } else if (field[1].startsWith("char")) {
                type = DBFField.FIELD_TYPE_C;
                try {
                    String lString = field[1].substring(
                            field[1].indexOf("(") + 1, field[1].indexOf(")"));
                    length = Integer.parseInt(lString);
                } catch (NumberFormatException e) {
                    // 不是数字，char后面的参数错误，抛出异常
                    throw new SqlException("char长度参数非数字类型");
                }
            }
            typeMap.put("length", length);
            typeMap.put("type", type);
            typeMap.put("constraint", 0);// 默认为0
            // 读取完整性约束条件
            if (field.length > 2) {
                isConstraint = true;
                // 数组个数超过2 ，表示有完整性约束
                int constraint = 0;
                for (int i = 2; i < field.length; i++) {
                    switch (field[i]) {
                        case "primary":
                            if ("key".equals(field[i + 1])) {
                                constraint += Create.Constraint.PRIMARY_KEY;
                                i++;
                            } else {
                                // key拼写错误，抛出异常
                                throw new SqlException("primary key中key拼写错误");
                            }
                            break;
                        case "not":
                            if ("null".equals(field[i + 1])) {
                                constraint += Create.Constraint.NOT_NULL;
                                i++;
                            } else {
                                // null拼写错误，抛出异常
                                throw new SqlException("not null中null拼写错误");
                            }
                            break;
                        case "unique":
                            constraint += Create.Constraint.UNIQUE;
                            break;
                        default:
                            break;
                    }
                }
                typeMap.put("constraint", constraint);

            }
            this.fields.add(typeMap);
            System.out.println("typeMap:" + typeMap);
        }
    }

    public static void main(String[] args) throws SqlException {
        String sql = "  create   table students (id int primary key unique not null , name char(11)   not  null, age int) ;";
        sql = sql.toLowerCase().replaceAll("\\s{2,}", " ");
        sql = sql.trim();

        System.out.println("sql:" + sql);
        String create = sql.substring(0, sql.indexOf(" "));
        System.out.println("create:" + create);
        sql = sql.substring(sql.indexOf(" ") + 1);
        System.out.println("sql:" + sql);
        String table = sql.substring(0, sql.indexOf(" "));
        System.out.println("table:" + table);

        sql = sql.substring(sql.indexOf(" ") + 1);
        String tableName = sql.substring(0, sql.indexOf("("));
        System.out.println("tableName:" + tableName);

        String fieldString = sql.substring(sql.indexOf("(") + 1, sql.indexOf(";")).trim();
        fieldString = fieldString.substring(0, fieldString.length() - 1);
        System.out.println("fieldString:" + fieldString);

        String[] fieldEntry = fieldString.split(",");
        for(int i = 0; i < fieldEntry.length;i++){
            fieldEntry[i] = fieldEntry[i].trim();
        }
        List<String[]> fieldList = new ArrayList<>();
        for (String fields : fieldEntry) {
            String[] field = fields.split(" ");
            fieldList.add(field);
        }
//        for (String[] str : fieldList) {
//            System.out.println(str[0]+"0");
//            System.out.println(str[1]+"1");
//        }
        TestTypeMap1 testTypeMap1 = new TestTypeMap1(fieldList,tableName);
        System.out.println(testTypeMap1.fields);


    }
}



