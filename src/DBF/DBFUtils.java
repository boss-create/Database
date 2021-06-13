/*
 * DBFUtils.java：DBF文件操作工具类，通过对javadbf4.1.jar中函数的调用，
 * 实现对DBF文件的读写操作，提供创建表，插入表和查询表的操作函数
 */


package DBF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import SqlExecute.DBMS;

public class DBFUtils {

    public DBFUtils() {

    }

    // 根据读取的信息创建表的各个字段属性
    public static void createDBF(String tableName,
                                 List<Map<String, Object>> typeFields) { //参数:表名, 记录每个字段记录的list
        OutputStream fos = null;
        try {
            // 定义DBF文件字段
            DBFField[] fields = new DBFField[typeFields.size()];
            // 分别定义各个字段信息  例如--ID  name  age
            for (int i = 0; i < typeFields.size(); i++) {
                fields[i] = new DBFField();
                fields[i].setName((String) typeFields.get(i).get("fieldName"));
                fields[i].setDataType((byte) typeFields.get(i).get("type"));
//                fields[i].setDataType(DBFField.FIELD_TYPE_C);
                fields[i].setFieldLength((int) typeFields.get(i).get("length"));
            }

            // 定义DBFWriter实例用来写DBF文件
            DBFWriter writer = new DBFWriter();
            // 把字段信息写入DBFWriter实例，即定义表结构
            writer.setFields(fields);
            // 定义输出流，并关联的一个文件
            fos = new FileOutputStream(DBMS.DATA_PATH + tableName + ".dbf");
            // 写入数据
            writer.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
//            log.error()
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 插入与表名相对应的字段的信息
    public static void insertDBF(String tableName, DBFContent dbfContent) { //这里把create设置的字段重写了需要改进
        OutputStream fos = null;
        try {
            // 定义DBF文件字段
            DBFField[] fields = new DBFField[dbfContent.getFields().size()];
            // 分别定义各个字段信息
            for (int i = 0; i < dbfContent.getFields().size(); i++) {
                fields[i] = new DBFField();
                fields[i].setName(dbfContent.getFields().get(i).getName());
                fields[i].setDataType(dbfContent.getFields().get(i).getDataType());
                fields[i].setFieldLength(dbfContent.getFields().get(i)
                        .getFieldLength());
                if (dbfContent.getFields().get(i).getDataType() == DBFField.FIELD_TYPE_N) {
                    fields[i].setDecimalCount(2);  //默认保留两位小数
                }
            }

            // 定义DBFWriter实例用来写DBF文件
            DBFWriter writer = new DBFWriter();
            // 把字段信息写入DBFWriter实例，即定义表结构
            writer.setFields(fields);

            // 一条条的写入记录
            Object[] rowData;
            for (int i = 0; i < dbfContent.getContents().size(); i++) {
                int rowSize = dbfContent.getFieldCount();
                rowData = new Object[rowSize];
                Map<String, Object> record = dbfContent.getContents().get(i); //一条记录
                for (int j = 0; j < dbfContent.getFields().size(); j++) {
                    String field = dbfContent.getFields().get(j).getName();
                    Object value = record.get(field);
                    if(dbfContent.getFields().get(j).getDataType() == DBFField.FIELD_TYPE_F) {
                        value = Double.parseDouble(record.get(field).toString());
                    }else if(dbfContent.getFields().get(j).getDataType() == DBFField.FIELD_TYPE_N){
                        System.out.print("record:"+record.get(field)+" ");
                        DecimalFormat df = new DecimalFormat("#.##");
                        value = Double.parseDouble(df.format(Double.parseDouble(record.get(field).toString())));
                    }
                    System.out.println("field = " + field + "   value = "
                            + value);
                    rowData[j] = value;
                }
                writer.addRecord(rowData);
            }

            // 定义输出流,关联我们前面已经创建好的想要插入表名文件
            File file = new File(DBMS.DATA_PATH + tableName + ".dbf");
            if (!file.exists())
                file.createNewFile();
            fos = new FileOutputStream(file);
            // 写入数据
            writer.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //从DBF文件中获取数据
    public static DBFContent getFileData(String tableName) {
        DBFReader reader = null;// 从dbf中获取内容
        List<Map<String, Object>> list = new LinkedList<>();
        DBFContent dbfContent = new DBFContent();  //实例化DBFContent
        InputStream in = null;
        try {
            in = new FileInputStream(new File(DBMS.DATA_PATH + tableName
                    + ".dbf"));
            reader = new DBFReader(in);// 将文件从文件流中读入。

            int fieldCount = reader.getFieldCount();// 读取字段个数
            int rowNum = reader.getRecordCount();// 获取有多少条记录

            // 设置DBFContent的参数
            dbfContent.setFieldCount(fieldCount);
            dbfContent.setRecordCount(rowNum);

            List<DBFField> fields = new ArrayList<>(); //得到每个字段
            for (int i = 0; i < fieldCount; i++) {
                // 得到DBF文件的Fields
                fields.add(reader.getField(i));
            }
            dbfContent.setFields(fields);

            Object[] rowObjects;
            List<Map<String, Object>> cons = new ArrayList<>();   //记录每条记录内容
            while ((rowObjects = reader.nextRecord()) != null) {  // 用Object[] 对象取出一行行记录
                Map<String, Object> map = new HashMap<>();        // map 为每个字段当前的字段名和所映射的这条记录一个toString
                for (int i = 0; i < fieldCount; i++) {
                    String s = rowObjects[i].toString().trim(); //去除列宽多余的空白
//                    System.out.println("s:" + s);
                    map.put(fields.get(i).getName(), s);

                }
                cons.add(map);
                System.out.println("map:" + map);
            }

            dbfContent.setContents(cons);  //将我们所读取到的记录数据初始化dbfContents
        } catch (FileNotFoundException e) {
            System.err.println("解析dbf数据过程中，没有找到dbf文件");  //标准错误输出流
            e.printStackTrace();
        } catch (DBFException e) {
            System.err.println("解析dbf数据过程中，dbf文件读取异常");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dbfContent;
    }
}