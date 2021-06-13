package TestAction;

import java.io.IOException;
import java.util.Scanner;
public class TestArr1 {
    public void cal(int[]arr,int index1, int index2){
        Scanner in = new Scanner(System.in);
        int sum1 = 0;
        try{
            index1 = in.nextInt();
            index2 = in.nextInt();
            sum1 = arr[index1]+arr[index2];
        }catch (ArrayIndexOutOfBoundsException | ArithmeticException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        int[] arr = {0, 1, 2, 3, 4};
    }
}
