package SqlParse;

public class Test {
    public static void main(String[] args){
        String testSql="select sname, age from student,sc where sage  >=20 and sage<=21 order by sno,sage desc;";
        SqlParserUtil test=new SqlParserUtil();
        String result=test.getParsedSql(testSql);
        System.out.println("result:"+result);
    }
}
