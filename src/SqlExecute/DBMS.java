package SqlExecute;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DBF.DBFContent;
import Server.MyServer;
import com.linuxense.javadbf.DBFField;
import module.Alter;
import module.Create;
import module.Delete;
import module.Drop;
import module.Insert;
import module.Select;
import module.Update;
import org.jetbrains.annotations.NotNull;
import FXInterface.DBMSForm;

public class DBMS {

    public static final String DATA_PATH = "data\\";// 数据库存储路径
    public static final String LOG_PATH = "systemLog\\";// 系统操作日志存储路径
    public static final String EXC_PATH = "exceptionLog\\"; //系统异常日志存储路径

    private DBMSForm form;// 显示窗口
    private MyServer server;
    public DBMS(){

    }
    public DBMS(DBMSForm form) {
        this.form = form;
    }
    public DBMS(MyServer server){
        this.server = server;
    }

    // 根据sql语句开头判断操作类型
    public void parseSQL(@NotNull String sql) throws SqlException {
        if (sql.trim().startsWith("create")) {  //trim 去掉两端空格
            Create create = parseCreate(sql);
            DBFContent content = create.executeSQL();
            server.setOutput(content, "建表成功");
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("insert")) {
            Insert insert = parseInsert(sql);
            DBFContent content = insert.executeSQL();
            server.setOutput(content, "插入成功");
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("delete")) {
            Delete delete = parseDelete(sql);
            DBFContent content = delete.executeSQL();
            server.setOutput(content, "删除成功");
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("update")) {
            Update update = parseUpdate(sql);
            DBFContent content = update.executeSQL();
            server.setOutput(content, "修改成功");
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("select")) {
            Select selectSql = parseSelect(sql);
            DBFContent content = new DBFContent();
            content = selectSql.executeSQL();
            if(selectSql.columns.get(0).equals("*")) {
                server.setOutput(content, null);
            }else{
                int flagCnt = 0;
                for(int i = 0; i < selectSql.columns.size(); i++){
                    boolean flag = false;
                    for(DBFField field:content.getFields()){
                        if(selectSql.columns.get(i).equals(field.getName())){
                            flag = true;
                            break;
                        }
                    }
                    if(flag) flagCnt++;
                }
                if(flagCnt == selectSql.columns.size()) {
                    server.setOutput(content, null, selectSql.columns);
                }else{
                    throw new SqlException("查询的列名不存在");
                }
            }
//            form.clearInput();
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("alter")) {
            Alter alter = parseAlter(sql);
            String title = alter.executeSQL();
            server.setOutput(title);
//            form.clearInput();
            recordSystemLogs(sql);
        } else if (sql.trim().toLowerCase().startsWith("drop")) {
            Drop drop = parseDrop(sql);
            String title = drop.executeSQL();
            server.setOutput(title);
//            form.clearInput();
            recordSystemLogs(sql);
        }else if(sql.trim().toLowerCase().equals("cls")){
            server.setOutput("");
        }else{
            throw new SqlException("非法的符号!");
        }
    }

    public String getStackTraceInfo(Exception e) {  //捕获printStackTrace转为字符串
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);//将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "printStackTrace()转换错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

    }

    // 记录系统日志
    public void recordSystemLogs(String sql) {
        File file = new File(LOG_PATH + "db1.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date date = new Date(System.currentTimeMillis());// 获取系统时间
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(file,true))){
            buf.write((date + " -->> : " + sql + "\n"));
            buf.flush();
        } catch (IOException e) {
//            e.printStackTrace();
            recordExceptionLogs(e);
        }
    }

    public void recordExceptionLogs(Exception ee){
        File f1 = new File(EXC_PATH+"exc1.log");
        if(!f1.exists()){
            try{
            f1.createNewFile();
        }catch(IOException e){
                e.printStackTrace();
            }
        }
        Date date = new Date(System.currentTimeMillis());
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(f1,true))){
            buf.write((date + " -->> : "+getStackTraceInfo(ee) + "\n"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Insert语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Insert parseInsert(String sql) throws SqlException {
        sql = sql.toLowerCase().replaceAll("\\s{2,}"," ").trim();
        if(sql.endsWith(";")){
            sql = sql.substring(0,sql.length()-1);
        }else{
            throw new SqlException("句子未以;结尾!");
        }
        String insert = sql.substring(0,sql.indexOf(" "));
        if(!insert.equals("insert")){
            throw new SqlException("insert 拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
        String into = sql.substring(0,sql.indexOf(" "));
        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0,sql.indexOf("("));
        tableName = tableName.trim();
        String fieldString = sql.substring(sql.indexOf("(") + 1,
                sql.indexOf(")"));
        fieldString = fieldString.replaceAll("\\s","");
        sql = sql.substring(sql.indexOf(")") + 1).trim();
        String values = sql.substring(0, sql.indexOf("(")).trim();
        if(!values.equals("values")){
            throw new SqlException("values 拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ") + 1);
        String valueString = sql.substring(sql.indexOf("(") + 1,
                sql.indexOf(")"));
        valueString = valueString.replaceAll("\\s","");
//        System.out.println("valueString1:"+valueString);

        return new Insert(tableName, fieldString, valueString);
    }

    // Create语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Create parseCreate(String sql) throws SqlException {
        sql = sql.toLowerCase().replaceAll("\\s{2,}", " ");
        sql = sql.trim();
        if(!sql.endsWith(";")){
            throw new SqlException("结尾缺少;");
        }
//        System.out.println("sql:"+sql);
        String create = sql.substring(0, sql.indexOf(" "));
//        System.out.println("create:"+create);
        if(!"create".equals(create)){
            throw new SqlException("create拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);
        String table = sql.substring(0,sql.indexOf(" "));
        if(!"table".equals(table)){
            throw new SqlException("table拼写错误!");
        }
//        System.out.println("table:"+table);

        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0, sql.indexOf("("));
//        System.out.println("tableName:"+tableName);

        String fieldString = sql.substring(sql.indexOf("(")+1,sql.indexOf(";")).trim();
        fieldString = fieldString.substring(0,fieldString.length()-1);
//        System.out.println("fieldString:"+fieldString);
        String[] fieldEntry = fieldString.split(",");

        int len = fieldEntry.length;
        for(int i = 0; i < len; i++){
            fieldEntry[i] = fieldEntry[i].trim();
        }
        List<String[]> list = new ArrayList<>();
        for (String fields : fieldEntry) {
            String[] field = fields.split(" ");
            list.add(field);
        }
        return new Create(list, tableName);
    }

    // Select语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public static Select parseSelect(String sql) throws SqlException {
        sql = sql.trim();
        sql = sql.toLowerCase();
        sql = sql.replaceAll("[\\s|,]{1,}", " ");

//        System.out.println("sql:" + sql);
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }else{
            throw new SqlException("句子未以;结尾");
        }
        String select = sql.substring(0,sql.indexOf(" "));
        if(!"select".equals(select)){
            throw new SqlException("select拼写错误!");
        }
        String[] selects = sql.split(" ");

        int length = selects.length;
        String[] selectCopy = new String[length];

        int cnt = 0;
        for(int i = 0; i < length; i++){
            if(i+2<length && (selects[i + 1].equals("<=") || selects[i + 1].equals(">=") || selects[i + 1].equals("="))){
                selectCopy[cnt++] = selects[i] + selects[i+1] + selects[i+2];
                i += 2;
            }
            else{
                selectCopy[cnt++] = selects[i];
            }
//            System.out.println("cnt:"+cnt);
        }

        int count = 0;
        for(int i = 0; i < cnt; i++){
            if(i+1<length && selectCopy[i+1]!=null && (selectCopy[i+1].startsWith(">=")|| selectCopy[i+1].startsWith("<=") || selectCopy[i+1].startsWith("="))){
                selectCopy[count++] = selectCopy[i] + selectCopy[i+1];
                i += 1;
            }else if(selectCopy[i].endsWith(">=") || selectCopy[i].endsWith("<=") || selectCopy[i].endsWith("=")){
                selectCopy[count++] = selectCopy[i]+selects[i+1];
                i += 1;
            }
            else{
                selectCopy[count++] = selectCopy[i];
            }
        }

        String[] selectEnd = new String[count];
        int start = 0,end = count-1;
        while(start <= end){
            selectEnd[start] = selectCopy[start];
            selectEnd[end] = selectCopy[end];
            start++;
            end--;
        }
        return new Select(selectEnd);
    }

    // Delete语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Delete parseDelete(String sql) throws SqlException {
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
//        System.out.println("sql:"+sql);

        if(sql.endsWith(";")){
            sql = sql.substring(0,sql.length()-1).trim();
        }else{
            throw new SqlException("句子未以;结尾!");
        }
        String delete = sql.substring(0, sql.indexOf(" "));
        if(!delete.equals("delete")){
            throw new SqlException("delete 拼写错误!");
        }
//        System.out.println("delete:"+delete);
        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);

        String from = sql.substring(0, sql.indexOf(" "));
        if(!from.equals("from")){
            throw new SqlException("from 拼写错误!");
        }
//        System.out.println("from:"+from);

        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);

        String tableName = sql.substring(0,sql.indexOf(" "));
//        System.out.println("tableName:"+tableName);
        String whereString = null;
        if(sql.contains("Condition")) {
            sql = sql.substring(sql.indexOf(" ") + 1);
//            System.out.println("sql:" + sql);


            String where = sql.substring(0, sql.indexOf(" "));
            if(!where.equals("where")){
                throw new SqlException("where 拼写错误!");
            }
//            System.out.println("Condition:" + Condition);

            sql = sql.substring(sql.indexOf(" ") + 1);
//            System.out.println("sql:" + sql);

            whereString = sql.trim().replaceAll("\\s+", "");
//            System.out.println("whereString:" + whereString);
        }
        return new Delete(tableName, whereString);
    }

    // Update语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Update parseUpdate(String sql) throws SqlException {
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
        System.out.println("sql:"+sql);
        if(!sql.endsWith(";")){
            throw new SqlException("句子没有以;结尾");
        }
        String update = sql.substring(0,sql.indexOf(" "));
//        System.out.println("update:"+update);
        if(!update.equals("update")){
            throw new SqlException("update 拼写错误");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0,sql.indexOf(" "));
//        System.out.println("tableName:"+tableName);

        sql = sql.substring(sql.indexOf(" ")+1);
        String set = sql.substring(0,sql.indexOf(" "));
//        System.out.println("set:"+set);
        if(!set.equals("set")){
            throw new SqlException("set 拼写错误");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);

        String updateString = null;
        String whereString = null;
        if(sql.contains("Condition")){
            updateString = sql.substring(0,sql.indexOf("Condition")).replaceAll("\\s+","");
//            System.out.println("updateString:"+updateString);
            sql = sql.substring((sql.indexOf("Condition")));
//            System.out.println("sql:"+sql);

            String where = sql.substring(sql.indexOf("Condition"),sql.indexOf(" "));
            System.out.println("where:"+where);
            if(!where.equals("where")){
                throw new SqlException("where 拼写错误");
            }
            whereString = sql.substring(sql.indexOf(" ")+1,sql.indexOf(";")).replaceAll("\\s+","");
//            System.out.println("whereString:"+whereString);
        }
        else{
            updateString = sql.substring(0,sql.length()-1).replaceAll("\\s+","");
//            System.out.println("updateString:"+updateString);
        }

        return new Update(tableName, updateString, whereString);
    }

    // Alter语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Alter parseAlter(String sql) throws SqlException {
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");

        if(!sql.endsWith(";")){
            throw new SqlException("句子没有以;结尾");
        }

        String alter = sql.substring(0,sql.indexOf(" "));
//        System.out.println("alter:"+alter);
        if(!alter.equals(alter)){
            throw new SqlException("alter 拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
//        System.out.println("sql:"+sql);
        String table = sql.substring(0,sql.indexOf(" "));
//        System.out.println("table:"+table);
        if(!table.equals("table")){
            throw new SqlException("table 拼写错误!");
        }
        sql = sql.substring(sql.indexOf(" ")+1);
        String tableName = sql.substring(0,sql.indexOf(" "));

        sql = sql.substring(sql.indexOf(" ")+1);
        String type = sql.substring(0,sql.indexOf(" "));
//        System.out.println("sql:"+sql);
//        System.out.println("type:"+type);

        sql = sql.substring(sql.indexOf(" ")+1,sql.length()-1).trim();

//        System.out.println("sql:"+sql);
        return new Alter(tableName, type, sql);
    }

    // Drop语句预处理，去除空白符，分号，根据语句特点分解提取语句信息
    public Drop parseDrop(String sql) throws SqlException {
        sql = sql.toLowerCase().trim();
        sql = sql.replaceAll("\\s{2,}"," ");
//        System.out.println("sql:"+sql);

        if(!sql.endsWith(";")){
            throw new SqlException("句子没有以;结尾");
        }
        String drop = sql.substring(0,sql.indexOf(" "));
//        System.out.println("drop:"+drop);

        sql = sql.substring(sql.indexOf(" ")+1);
        String table = sql.substring(0,sql.indexOf(" "));
//        System.out.println("table:"+table);

        String tableName = sql.substring(sql.indexOf(" ")+1,sql.length()-1).trim();
//        System.out.println("tableName:"+tableName);

        return new Drop(tableName);
    }
}
