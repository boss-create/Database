package TestSelect;
import java.util.ArrayList;
import java.util.List;

import SqlExecute.SqlException;
import DBF.DBFContent;
import module.Select;
import Condition.Or;
public class TestSelect1 {
    private String[] originalSql;// 源sql字符串
    private int columnIndex,fromIndex, whereIndex, andIndex,orIndex,groupIndex, orderIndex;// 各个标志单词在sql分解成的字符串数组中的下标位置
    public List<Select.SelectField> selectFields;// 要查询的字段名称
    public List<String> columnNameList,tableNameList, whereList, groups, orderList;// Select语句中的各个语句块字符串
    private List<Or> conditions;// 由Where语句生成的Or条件组
    private DBFContent dbfContent;// 文件读取的数据库内容

    public TestSelect1(String[] selects) throws SqlException {
        originalSql = selects;
        DBFContent dbfContent = new DBFContent();
        fromIndex = whereIndex = groupIndex = orderIndex = -1;
        selectFields = new ArrayList<>();
        columnNameList = new ArrayList<>();
        tableNameList = new ArrayList<>();
        whereList = new ArrayList<>();

    }

    private void init(){
        int len = originalSql.length;
        for(int i = 2; i < len; i++){
            switch (originalSql[i]){
                case "from" ->{
                    fromIndex = i;
                    break;
                }
                case "Condition" ->{
                    whereIndex = i;
                    break;
                }
                case "and" ->{
                    andIndex = i;
                    break;
                }
                case "or" ->{
                    orIndex = i;
                    break;
                }
            }
        }
        for(int i = 1; i < fromIndex; i++){
            columnNameList.add(originalSql[i]);
        }
        if(whereIndex != -1) {
            for (int i = fromIndex + 1; i < whereIndex; i++){
                tableNameList.add(originalSql[i]);
            }
        }


    }
    public static void main(String[] args){

    }
}
