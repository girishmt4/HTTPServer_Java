package root;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
  String loggerFilePath;
  
  public Logger(String filePath) {
    this.loggerFilePath=filePath;
  }
  
  public void writeLog(Socket socket,Request request,Response response) {
    File logFile = new File(loggerFilePath);
    if (!logFile.exists()) {
      try {
        logFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    try {
      FileWriter fileWriter=new FileWriter(logFile.getAbsoluteFile(),true);
      BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
      String clientIPAddress=socket.getInetAddress().toString();
      SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
      Date date=new Date();
      String totalBytes=null;
      if(response.getHeaders()!=null && response.getHeaders().containsKey("Content-Length")){
        totalBytes = response.getHeaders().get("Content-Length");
      }
      else{
        totalBytes = "0";
      }
      
      bufferedWriter.write("\n"+clientIPAddress+" - ["+dateFormat.format(date)+"] \""+request.getRequestLine()+"\" "+response.getResponseCode()+" "+totalBytes+"\n");
      System.out.println("\n"+clientIPAddress+" - ["+dateFormat.format(date)+"] \""+request.getRequestLine()+"\" "+response.getResponseCode()+" "+totalBytes+"\n");
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
