package root;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
  private HttpdConf configuration;
  private MimeTypes mimeTypes;
  private ServerSocket serverSocket;
  
  public WebServer() {
    configuration=new HttpdConf(".//src//conf//httpd.conf");
    configuration.load();
    mimeTypes=new MimeTypes(".//src//conf//mime.types");
    mimeTypes.load();
  }
  
  public void start() throws IOException {
	  ServerSocket serverSocket=new ServerSocket(configuration.listen);
    System.out.println("Waiting at port "+configuration.listen);
	  while(true){
      try {
        Socket socket=serverSocket.accept();
        Worker worker=new Worker(socket, configuration, mimeTypes);
        Thread thread = new Thread(worker);
        thread.start();
      }
      catch (IOException e) {
        e.printStackTrace();
    }  
  }
}
  
  public static void main(String[] args) throws IOException {
    WebServer webServer=new WebServer();
    webServer.start();	
  }
}
