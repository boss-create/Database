package TestAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;



import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;


public class TestWriteDBF1 {
    public static void main(String[] args){
        writeDBF("src\\TestModule.TestAction\\db1.dbf");
    }
    public static void writeDBF(String path) {
        OutputStream fos = null;
        try {
//             定义DBF文件字段
            DBFField[] fields = new DBFField[3];
            // 分别定义各个字段信息，setFieldName和setName作用相同,
            // 只是setFieldName已经不建议使用
            fields[0] = new DBFField();
            // fields[0].setFieldName("emp_code");
            fields[0].setName("semp_code");
            fields[0].setDataType(DBFField.FIELD_TYPE_C);
            fields[0].setFieldLength(10);
            fields[1] = new DBFField();
            // fields[1].setFieldName("emp_name");
            fields[1].setName("emp_name");
            fields[1].setDataType(DBFField.FIELD_TYPE_C);
            fields[1].setFieldLength(20);
            fields[2] = new DBFField();
            // fields[2].setFieldName("salary");
            fields[2].setName("salary");
            fields[2].setDataType(DBFField.FIELD_TYPE_N);
            fields[2].setFieldLength(12);
            fields[2].setDecimalCount(2);
         //    定义DBFWriter实例用来写DBF文件
//            DBFField[] fields = new DBFField[3];
            DBFWriter writer = new DBFWriter();
            // 把字段信息写入DBFWriter实例，即定义表结构
            writer.setFields(fields);
            // 一条条的写入记录
            Object[] rowData = new Object[3];
            rowData[0] = "900";
            rowData[1] = "John1";
            rowData[2] = 5000.00;
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1001";
            rowData[1] = "Lalit";
            rowData[2] = 3400.00;
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1002";
            rowData[1] = "Rohit";
            rowData[2] = 7350.00;
            writer.addRecord(rowData);
            // 定义输出流，并关联的一个文件
            fos = new FileOutputStream(path);
            // 写入数据
            writer.write(fos);
            // writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
