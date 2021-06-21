package Server;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import DBF.DBFContent;
import SqlExecute.DBMS;
import SqlExecute.SqlException;
import com.linuxense.javadbf.DBFField;

import javax.swing.*;

public class MyServer {
    JFrame jf;
    JPanel panel;
    JTextArea text;

    public MyServer(){
        dbmsForm();
    }

    public void dbmsForm(){
        jf = new JFrame("结果显示框");
        jf.setSize(800,600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int windowWidth = jf.getWidth(); // 获取窗口的宽
        int windowHeight = jf.getHeight(); // 获取窗口的高
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenHeight =  screenSize.height;
        int screenWidth = screenSize.width;
        jf.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
                - windowHeight / 2);// 设置窗口居中显示
        panel = new JPanel();
        jf.setContentPane(panel);

        text = new JTextArea(50,50);
        text.setLineWrap(true);// 设置自动换行
        text.setEditable(false);// 设置不可编辑
        text.setSize(780, 470);// 设置大小
        text.setLocation(0, 0);// 设置位置
        panel.add(text);
        jf.setVisible(true);
    }

    public void setOutput(DBFContent content, String title) {
        StringBuffer builder = new StringBuffer();
        if (title != null) {
            builder.append(
                    "------------------------------------------------------------------------------------------")
                    .append(title)
                    .append("--------------------------------------------------------------------------------------\n");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        java.util.List<DBFField> fields = content.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if (i == fields.size() - 1) {
                builder.append("      ").append(fields.get(i).getName())
                        .append("\n");
            } else
                builder.append("      ").append(fields.get(i).getName())
                        .append("\t");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < content.getRecordCount(); i++) {
            Map<String, Object> map = content.getContents().get(i);
            for (int j = 0; j < fields.size(); j++) {
                if (j == fields.size() - 1) {
                    builder.append("     ")
                            .append(map.get(fields.get(j).getName()))
                            .append("\n");
                } else
                    builder.append("     ")
                            .append(map.get(fields.get(j).getName()))
                            .append("\t");
            }
            builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        text.setText(builder.toString());
    }

    public void setOutput(DBFContent dbfContent,String title, java.util.List<String> columns){
        StringBuffer builder = new StringBuffer();
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                builder.append("      ").append(columns.get(i))
                        .append("\n");
            } else
                builder.append("      ").append(columns.get(i))
                        .append("\t");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        java.util.List<Map<String, Object>> content = dbfContent.getContents();
        List<DBFField> fields = dbfContent.getFields();
        //for(int k = 0; k < fields.size(); k++) {

        //if (content.get(k).equals(content.get(j))) {
        for (int i = 0; i < content.size(); i++) {
            for(int j = 0; j < columns.size(); j++) {
                if (j == columns.size() - 1) {
                    builder.append("      ").append(content.get(i).get(columns.get(j)))
                            .append("\n");
                } else {
                    builder.append("      ").append(content.get(i).get(columns.get(j)))
                            .append("\t");
                }
            }
            builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

        text.setText(builder.toString());

    }

    public void setOutput(String output) {
        text.setText("------------------------------------------------------------------------------------------"
                + output
                + "------------------------------------------------------------------------------------------");
    }

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(1004);
            Socket client = serverSocket.accept();
            System.out.println("服务器已启动");
            MyServer server = new MyServer();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            String line;
            DBMS dbms = new DBMS(server);

            while((line=in.readLine()) != null){
                System.out.println("传入语句:"+line);
                try {
                    dbms.parseSQL(line);
                }catch (SqlException e){
                    server.text.setText(e.getMessage());
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
