package TestModule;

import SqlExecute.DBMS;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import DBF.DBFContent;
import module.Select;
import Condition.Or;

import java.io.*;
import java.util.*;

public class TestDBFRead1 {
    private String[] originalSql;// 源sql字符串
    private int columnIndex,fromIndex, whereIndex, groupIndex, orderIndex;// 各个标志单词在sql分解成的字符串数组中的下标位置
    public List<Select.SelectField> selectFields;// 要查询的字段名称
    public List<String> columns,froms, wheres, groups, orders;// Select语句中的各个语句块字符串
    private List<Or> conditions;// 由Where语句生成的Or条件组
    private DBFContent dbfContent;// 文件读取的数据库内容
    private String columnName;

    public static void main(String[] args){
        DBFReader reader = null;// 从dbf中获取内容
        List<Map<String, Object>> list = new LinkedList<>();
        DBFContent dbfContent = new DBFContent();  //实例化DBFContent
        InputStream in = null;
        try {
            in = new FileInputStream(new File(DBMS.DATA_PATH + "student"
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

            for(DBFField field:fields){
                System.out.print("fieldName:"+field.getName()+",");
            }
            System.out.println();


            Object[] rowObjects;
            List<Map<String, Object>> cons = new ArrayList<>();   //记录每条记录内容
            while ((rowObjects = reader.nextRecord()) != null) {  // 用Object[] 对象取出一行行记录
                Map<String, Object> map = new HashMap<>();        // map 为每个字段当前的字段名和所映射的这条记录一个toString
                for (int i = 0; i < fieldCount; i++) {
                    String s = rowObjects[i].toString().trim(); //去除列宽多余的空白
                    System.out.println("s:"+s);

                    map.put(fields.get(i).getName(), s);

                }
                cons.add(map);
                System.out.println("map:"+map);
            }

            System.out.println("cons:"+cons);
            dbfContent.setContents(cons);  //将我们所读取到的记录数据初始化dbfContents
            System.out.println("cons:"+cons);
            for(int i = 0; i < cons.size(); i++){
                System.out.println(cons.get(i).get("sname"));
            }


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


    }
    }
