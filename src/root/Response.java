package root;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Response {
  private int responseCode;
  private String reasonPhrase;
  private Map<String,String> headers;
  private byte[] body;
  
  public Response() {
    this.headers=new HashMap<String,String>();
  }
  
  public int getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }

  public void setReasonPhrase(String reasonPhrase) {
    this.reasonPhrase = reasonPhrase;
  }

  public Map<String,String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String,String> headers) {
    this.headers = headers;
  }

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }
  
  public String formatDate(Date date)
  {
    DateFormat dateFormat=new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss.SSSZ");
    return dateFormat.format(date);
  }
  
  public void send(OutputStream outputStream) throws IOException {
    StringBuilder completeResponse=new StringBuilder();
    completeResponse.append("HTTP/1.1");
    completeResponse.append(" "+getResponseCode());
    completeResponse.append(" "+getReasonPhrase());
    completeResponse.append("\r\n");
    getHeaders().put("Server","Girish's Server");
    getHeaders().put("Connection","Closed");
    if(getHeaders()!=null) {
      Iterator<Entry<String,String>> i=getHeaders().entrySet().iterator();
      while(i.hasNext()) {
        Entry<String,String> nextHeader=i.next();
        completeResponse.append(nextHeader.getKey());
        completeResponse.append(": ");
        completeResponse.append(nextHeader.getValue());
        completeResponse.append("\r\n");
      }
      completeResponse.append("\r\n");
    }
    byte[] responseArray=completeResponse.toString().getBytes();
    if(body!=null){
		  byte[] responseArrayWithBody = new byte[responseArray.length + body.length];
		  System.arraycopy(responseArray, 0, responseArrayWithBody, 0, responseArray.length);
		  System.arraycopy(body, 0, responseArrayWithBody,responseArray.length, body.length);
		  outputStream.write(responseArrayWithBody);
	  }
    else {
    	outputStream.write(responseArray);
    }
    outputStream.close();
    System.out.println("--------Response--------\n"+completeResponse);
  }
  
  
  
  public byte[] getContentsFromFile(File file) throws FileNotFoundException, IOException {
    FileInputStream fileInputStream=new FileInputStream(file);
    byte[] contentsFromFile=null;
    contentsFromFile=new byte[(int)file.length()];
    fileInputStream.read(contentsFromFile);
    headers.put("Content-Length",Integer.toString(contentsFromFile.length));
    getHeaders().put("Last-Modified",formatDate(new Date(file.lastModified())));
    //System.out.println(contentsFromFile);
    return contentsFromFile;
  }
  
  
}
