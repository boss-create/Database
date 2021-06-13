package module;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linuxense.javadbf.DBFField;
import SqlExecute.DBMS;
import SqlExecute.SqlException;
import DBF.DBFContent;
import DBF.DBFUtils;
import org.jetbrains.annotations.NotNull;

public class Create {

    private List<Map<String, Object>> typeFields;// 一个表里所有的typeMap
    private String tableName;//表名
    private boolean isConstraint = false;//是否有约束条件

    public Create(List<String[]> fields, String tableName) throws SqlException {
        super();
        this.tableName = tableName;
        this.typeFields = new ArrayList<Map<String, Object>>();
        init(fields);
    }

    public static class Constraint {

        public static final int PRIMARY_KEY = 1;
        public static final int UNIQUE = 2;
        public static final int NOT_NULL = 4;
    }

    public List<Map<String, Object>> getTypeFields() {
        return typeFields;
    }

    private void init(@NotNull List<String[]> fields) throws SqlException {  //fields 对应的是table 每个字段的属性数组(包括属性名)
        for (String[] field : fields) {
            Map<String, Object> typeMap = new HashMap<>();
            typeMap.put("fieldName", field[0]);
            byte type = 0;
            int length = 0;
            if (field[1].equals("int")) {
                type = DBFField.FIELD_TYPE_F;
                length = 10; //默认长度为10
            } else if (field[1].equals("double")) {
                type = DBFField.FIELD_TYPE_N;
                length = 15;
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
                                constraint += Constraint.PRIMARY_KEY;
                                i++;
                            } else {
                                // key拼写错误，抛出异常
                                throw new SqlException("key拼写错误");
                            }
                            break;
                        case "not":
                            if ("null".equals(field[i + 1])) {
                                constraint += Constraint.NOT_NULL;
                                i++;
                            } else {
                                // null拼写错误，抛出异常
                                throw new SqlException("null拼写错误");
                            }
                            break;
                        case "unique":
                            constraint += Constraint.UNIQUE;
                            break;
                        default:
                            break;
                    }
                }
                typeMap.put("constraint", constraint);
            }
            this.typeFields.add(typeMap);
        }
    }

    public DBFContent executeSQL() throws SqlException {  //根据sql语句记录的数据类型 执行sql语句创建表
        File file = new File(DBMS.DATA_PATH + tableName + ".dbf");
        if (file.exists()) {
            // 表已存在，抛出异常
            throw new SqlException("表已存在");
        }
        DBFUtils.createDBF(tableName, typeFields);
        if (isConstraint)
            inputConstraint();
        DBFContent dbfContent = DBFUtils.getFileData(tableName);  //用tableName 初始化读取dbf得到基本的属性
        dbfContent.setTableName(tableName);
//        System.out.println(content.getFields());
        return dbfContent;
    }

    //读取约束性条件表，数据字典
    private void inputConstraint() {
        File file = new File(DBMS.DATA_PATH + tableName+"Constraint.dbf");
        if (!file.exists()) {//文件不存在，创建，表中有tableName,fieldName,PrimaryKey,Unique,NotNull五个属性
            DBFContent dbfContent = new DBFContent();
            dbfContent.setFieldCount(5);
            int size = typeFields.size();  //typeMap的数量-----一个table里所有的属性数
            dbfContent.setRecordCount(size);
//            System.out.println("field = " + fields);
          /*  得到fields的fieldName constraint的数值  通过这两个参数调用getConstraintRecord转化constraint  生成constraint的typeMap */
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < typeFields.size(); i++) {
                String fieldName = (String) typeFields.get(i).get("fieldName");
//                System.out.println(fields);
                int constraint = (Integer) typeFields.get(i).get("constraint");
                Map<String, Object> map = getConstraintRecord(fieldName,
                        constraint);
//                System.out.println(" map init = " + map);
                list.add(map);
            }
            dbfContent.setContents(list);
//            dbfContent.setContents(fields);
            List<DBFField> dbfFields = new ArrayList<>();

            DBFField dbfField = new DBFField();
            dbfField.setName("fieldName");
            dbfField.setDataType(DBFField.FIELD_TYPE_C);
            dbfField.setFieldLength(10);
            dbfFields.add(dbfField);

            dbfField = new DBFField();
            dbfField.setName("tableName");
            dbfField.setDataType(DBFField.FIELD_TYPE_C);
            dbfField.setFieldLength(10);
            dbfFields.add(dbfField);

            dbfField = new DBFField();
            dbfField.setName("PrimaryKey");
            dbfField.setDataType(DBFField.FIELD_TYPE_C);
            dbfField.setFieldLength(10);
            dbfFields.add(dbfField);

            dbfField = new DBFField();
            dbfField.setName("Unique");
            dbfField.setDataType(DBFField.FIELD_TYPE_C);
            dbfField.setFieldLength(10);
            dbfFields.add(dbfField);

            dbfField = new DBFField();
            dbfField.setName("NotNull");
            dbfField.setDataType(DBFField.FIELD_TYPE_C);
            dbfField.setFieldLength(10);
            dbfFields.add(dbfField);

            dbfContent.setFields(dbfFields);

            DBFUtils.insertDBF(tableName+"Constraint", dbfContent);
        } else {
            //将fields中的约束性条件字符串填入到数据字典中
            DBFContent dbfContent = DBFUtils.getFileData(tableName+"Constraint");
            for (Map<String, Object> typeMap : typeFields) {
                String fieldName = typeMap.get("fieldName").toString();
                int constraint = Integer.parseInt(typeMap.get("constraint").toString());
                Map<String, Object> map = getConstraintRecord(fieldName,
                        constraint);
                dbfContent.getContents().add(map);
                dbfContent.setRecordCount(dbfContent.getRecordCount() + 1);
            }
            DBFUtils.insertDBF(tableName+"Constraint", dbfContent);
        }
    }

    //获取完整性约束条件
    private Map<String, Object> getConstraintRecord(String fieldName,
                                                    int constraint) {
        switch (constraint) { //primaryKey
            case 1 -> {
                Map<String, Object> typeMap1 = new HashMap<>();
                typeMap1.put("fieldName", fieldName);
                typeMap1.put("tableName", tableName);
                typeMap1.put("PrimaryKey", true + "");
                typeMap1.put("Unique", false + "");
                typeMap1.put("NotNull", false + "");
                System.out.println("map1 = " + typeMap1);
                return typeMap1;
            }
            case 2 -> {  //unique
                Map<String, Object> typeMap2 = new HashMap<>();
                typeMap2.put("fieldName", fieldName);
                typeMap2.put("tableName", tableName);
                typeMap2.put("PrimaryKey", false + "");
                typeMap2.put("Unique", true + "");
                typeMap2.put("NotNull", false + "");
                System.out.println("map2 = " + typeMap2);
                return typeMap2;
            }
            case 3 -> { //primaryKey+unique
                Map<String, Object> map3 = new HashMap<>();
                map3.put("fieldName", fieldName);
                map3.put("tableName", tableName);
                map3.put("PrimaryKey", true + "");
                map3.put("Unique", true + "");
                map3.put("NotNull", false + "");
                System.out.println("map3 = " + map3);
                return map3;
            }
            case 4 -> { //notNull
                Map<String, Object> map4 = new HashMap<>();
                map4.put("fieldName", fieldName);
                map4.put("tableName", tableName);
                map4.put("PrimaryKey", false + "");
                map4.put("Unique", false + "");
                map4.put("NotNull", true + "");
                System.out.println("map4 = " + map4);
                return map4;
            }
            case 5 -> { //primaryKey+not null
                Map<String, Object> map5 = new HashMap<>();
                map5.put("fieldName", fieldName);
                map5.put("tableName", tableName);
                map5.put("PrimaryKey", true + "");
                map5.put("Unique", false + "");
                map5.put("NotNull", true + "");
                System.out.println("map5 = " + map5);
                return map5;
            }
            case 6 -> { //unique+not null
                Map<String, Object> map6 = new HashMap<>();
                map6.put("fieldName", fieldName);
                map6.put("tableName", tableName);
                map6.put("PrimaryKey", false + "");
                map6.put("Unique", true + "");
                map6.put("NotNull", true + "");
                System.out.println("map6 = " + map6);
                return map6;
            }
            case 7 -> { //primaryKey+unique+not null
                Map<String, Object> map7 = new HashMap<>();
                map7.put("fieldName", fieldName);
                map7.put("tableName", tableName);
                map7.put("PrimaryKey", true + "");
                map7.put("Unique", true + "");
                map7.put("NotNull", true + "");
                System.out.println("map7 = " + map7);
                return map7;
            }
            default -> {  //无完整性约束条件
                Map<String, Object> map0 = new HashMap<>();
                map0.put("fieldName", fieldName);
                map0.put("tableName", tableName);
                map0.put("PrimaryKey", false + "");
                map0.put("Unique", false + "");
                map0.put("NotNull", false + "");
                System.out.println("map1 = " + map0);
                return map0;
            }
        }
    }

}
