package module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import SqlExecute.DBMS;
import SqlExecute.SqlException;
import DBF.DBFContent;
import DBF.DBFUtils;
import util.StringTools;
import Condition.And;
import Condition.Or;

public class Delete {

    private String tableName;// 表名
    public List<String> wheres;// Where语句数组
    private boolean isWhere = false;// 是否为Where语句
    private List<Or> conditions;// 由Where语句数组生成的OR条件组
    private DBFContent content;// 数据库文件信息

    public Delete(String tableName, String whereString) throws SqlException {
        this.tableName = tableName;
        wheres = new ArrayList<>();
        conditions = new ArrayList<>();
        File file = new File(DBMS.DATA_PATH + tableName + ".dbf");
        if (!file.exists()) {
            // 表不存在，抛出异常
            throw new SqlException("表不存在");
        }
        content = DBFUtils.getFileData(tableName);
        if (whereString == null)
            isWhere = false;
        else {
            isWhere = true;
            String[] temp = whereString.split(" ");
            for (String tempChild : temp) {
                wheres.add(tempChild);
            }
            translateWhere();
        }
    }

    public DBFContent executeSQL() throws SqlException {// 执行语句
        // 删除符合条件的记录
        DBFContent resultContent = new DBFContent();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < content.getRecordCount(); j++) {
            int flag = 0;
            for (int k = 0; k < conditions.size(); k++) {
                System.out.println("j = " + j);
                if (!conditions.get(k).judgeCondition(
                        content.getContents().get(j))) {
                    continue;
                } else {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                System.out.println(content.getContents().get(j));
            } else {
                System.out.println(content.getContents().get(j));
                list.add(content.getContents().get(j));
            }
        }
        System.out.println(content);
        resultContent.setContents(list);
        resultContent.setFieldCount(content.getFieldCount());
        resultContent.setFields(content.getFields());
        resultContent.setRecordCount(list.size());
        DBFUtils.insertDBF(tableName, resultContent);
        return resultContent;
    }

    // 由Where语句组翻译成OR条件组
    private void translateWhere() throws SqlException {// froms.get()多表查询
        conditions = new ArrayList<Or>();
        Or or = new Or(tableName);
        for (int i = 0; i < wheres.size(); i++) {
            String w1 = wheres.get(i);
            switch (w1) {
                case "between":
                    And and1 = new And(tableName, content.getFields());
                    and1.setType(And.BETWEEN);
                    and1.setCount(2);
                    and1.setOther(wheres.get(i - 1));
                    int[] flags = new int[2];
                    String[] fields = new String[2];

                    if (StringTools.isFieldName(wheres.get(i + 1))) {
                        flags[0] = And.FLAGS_FIELD;
                        fields[0] = wheres.get(i + 1);
                    } else {
                        flags[0] = And.FLAGS_VALUE;
                        String temp = wheres.get(i + 1);
                        if (temp.contains("'"))
                            temp = temp.replace("'", "");
                        if (temp.contains("'"))
                            temp = temp.replace("'", "");
                        fields[0] = temp;
                        System.out.println("temp  =  " + temp);
                    }

                    if (!wheres.get(i + 2).equals("and")) {
                        // and输入错误 抛出异常
                        throw new SqlException("and拼写错误");
                    }

                    if (StringTools.isFieldName(wheres.get(i + 3))) {
                        flags[1] = And.FLAGS_FIELD;
                        fields[1] = wheres.get(i + 3);
                    } else {
                        flags[1] = And.FLAGS_VALUE;
                        String temp = wheres.get(i + 3);
                        if (temp.contains("'"))
                            temp = temp.replace("'", "");
                        if (temp.contains("'"))
                            temp = temp.replace("'", "");
                        fields[1] = temp;
                        System.out.println("temp  =  " + temp);
                    }
                    and1.setFlags(flags);
                    and1.setFields(fields);
                    or.addAnd(and1);
                    i += 3;
                    break;
                case "and":

                    break;
                case "or":
                    conditions.add(or);
                    or = new Or(tableName);
                    break;
                default:
                    And andOfWhere = new And(tableName,
                            content.getFields());
                    if (w1.contains("<>")) {
                        singleAndTranslate(or, w1, andOfWhere, "<>");
                    } else if (w1.contains("<=")) {
                        singleAndTranslate(or, w1, andOfWhere, "<=");
                    } else if (w1.contains(">=")) {
                        singleAndTranslate(or, w1, andOfWhere, ">=");
                    } else if (w1.contains("<")) {
                        singleAndTranslate(or, w1, andOfWhere, "<");
                    } else if (w1.contains(">")) {
                        singleAndTranslate(or, w1, andOfWhere, ">");
                    } else if (w1.contains("=")) {
                        singleAndTranslate(or, w1, andOfWhere, "=");
                    } else {
                        // 语句错误，抛出异常
                        throw new SqlException("含有该数据库系统不支持的条件语句");
                    }
                    break;
            }
        }
        conditions.add(or);
    }

    public void singleAndTranslate(Or or, String w1,
                                   And andOfWhere, String type) throws SqlException {
        String[] temp = w1.split(type);
        if (temp.length != 2) {
            // 参数数目不正确，抛出异常
            System.out.println(" 参数数目不正确，抛出异常");
            throw new SqlException("条件参数数目不正确");
        } else {
            int[] flags1 = new int[2];
            String[] fields1 = new String[2];
            for (int j = 0; j < 2; j++) {
                if (StringTools.isFieldName(temp[j])) {
                    flags1[j] = And.FLAGS_FIELD;
                    fields1[j] = temp[j];
                    System.out.println("temp " + temp[j]);
                } else {
                    flags1[j] = And.FLAGS_VALUE;
                    if (temp[j].contains("'"))
                        temp[j] = temp[j].replace("'", "");
                    if (temp[j].contains("'"))
                        temp[j] = temp[j].replace("'", "");
                    fields1[j] = temp[j];
                    System.out.println("temp " + temp[j]);
                }
            }
            andOfWhere.setType(And.getAndType(type));
            andOfWhere.setCount(2);
            andOfWhere.setFields(fields1);
            andOfWhere.setFlags(flags1);
            or.addAnd(andOfWhere);
        }
    }

}
