package root;

import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;

import responses.ResponseOK;


public class Worker implements Runnable{
  
  private Thread thread;
  private Socket socket;
  private HttpdConf configuration;
  private MimeTypes mimeTypes;
  private Logger logger;
  
  public Worker(Socket socket, HttpdConf configuration, MimeTypes mimeTypes) {
    this.socket = socket;
    this.configuration = configuration;
    this.mimeTypes = mimeTypes;
  }

  public void run() {
    this.logger=new Logger("./logs/log.txt");
    Request request;
    try {
      request = new Request(this.socket.getInputStream());
      Response badResponse = request.isBadRequest();
      if(badResponse!=null){
        badResponse.send(socket.getOutputStream());
        logger.writeLog(socket,request,badResponse);
        socket.close();
        return;
      }
      Resource resource=new Resource(request.getUri(),configuration,mimeTypes);
      resource.fullPath();
      Response response=ResponseFactory.getResponse(request, resource);
      response.send(socket.getOutputStream());
      logger.writeLog(socket,request,response);
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
