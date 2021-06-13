package TestModule;

import com.linuxense.javadbf.DBFField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDBFWriter1 {
    public static void main(String[] args){
        List<Map<String, Object>> dbfContent = new ArrayList<>();
        DBFField[] fields = new DBFField[dbfContent.size()];
        // 分别定义各个字段信息
        for (int i = 0; i < dbfContent.size(); i++) {
            fields[i] = new DBFField();
            fields[i].setName((String) dbfContent.get(i).get("fieldName"));
            fields[i].setDataType((byte) dbfContent.get(i).get("type"));
//                fields[i].setDataType(DBFField.FIELD_TYPE_C);
            fields[i].setFieldLength((int) dbfContent.get(i).get("length"));
        }

    }
}
