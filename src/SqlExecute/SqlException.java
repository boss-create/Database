/*
* 自定义的处理sql语句语法的异常
* */
package SqlExecute;

public class SqlException extends Exception {

    public SqlException(String message) {
        super(message);
    }

}
