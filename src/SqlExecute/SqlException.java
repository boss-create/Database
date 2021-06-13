/*
* 自定义的处理sql语句语法的异常
* */
package SqlExecute;

public class SqlException extends Exception {

    public String exception;

    public SqlException(String string) {
        exception = string;
    }

}
