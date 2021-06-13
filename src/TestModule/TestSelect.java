package TestModule;

import java.util.Arrays;

public class TestSelect {
    public static void main(String[] args) {
//        String sql = " select * from  student ,  sc Condition sage>= 20 and sage <=21 order by sno, sage desc ; ";
        String sql = "select * from person where age = 20;";
        sql = sql.trim();
        sql = sql.toLowerCase();
        sql = sql.replaceAll("[\\s|,]{1,}", " ");

        System.out.println("sql:" + sql);
        if (sql.endsWith(";"))
            sql = sql.substring(0, sql.length() - 1);
        String[] select = sql.split(" ");

        int length = select.length;
        String[] selectCopy = new String[length];

        int cnt = 0;
       for(int i = 0; i < length; i++){
           if(i+2<length && (select[i + 1].equals("<=") || select[i + 1].equals(">=") || select[i + 1].equals("="))){
               selectCopy[cnt++] = select[i] + select[i+1] + select[i+2];
               i += 2;
           }
           else{
               selectCopy[cnt++] = select[i];
           }
           System.out.println("cnt:"+cnt);
       }
       int count = 0;
       for(String str:selectCopy)
       System.out.println("selectCopy:"+str);
       for(int i = 0; i < cnt; i++){
           if(i+1<length&&selectCopy[i+1]!= null && (selectCopy[i+1].startsWith(">=")|| selectCopy[i+1].startsWith("<=") || selectCopy[i+1].startsWith("="))){
               selectCopy[count++] = selectCopy[i] + selectCopy[i+1];
               i += 1;
           }else if(selectCopy[i].endsWith(">=") || selectCopy[i].endsWith("<=") || selectCopy[i].endsWith("=")){
               selectCopy[count++] = selectCopy[i]+select[i+1];
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
      for(String str:selectEnd){
          System.out.print(str+",");
      }


    }
}
