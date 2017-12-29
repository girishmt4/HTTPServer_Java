package root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import responses.ResponseBadRequest;



public class Request {
  private String uri, body, verb, httpVersion,requestLine;
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  private Map<String,String> headers;
  private String queryString;
  
  
  public Request(InputStream inputStream) {
    setHeaders(new HashMap<String,String>());
    this.body = "";
    this.parse(inputStream);  
	}
  
  public void parse(InputStream inputStream) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      this.requestLine = bufferedReader.readLine();
      System.out.println(requestLine);
      String[] requestElements=requestLine.split(" ");
      setVerb(requestElements[0]);
      setUri(requestElements[1]);
      setHttpVersion(requestElements[2]);
      String nextHeaderLine=bufferedReader.readLine();
      String[] host=nextHeaderLine.split(" ");
      getHeaders().put(host[0].substring(0,host[0].indexOf(':')),host[1]);
      while(!(nextHeaderLine = bufferedReader.readLine()).equals("")) {       	
        System.out.println(nextHeaderLine);
      	if(nextHeaderLine.contains(":"))
        {
          String[] nextHeader = nextHeaderLine.split(":");
          getHeaders().put(nextHeader[0],nextHeader[1].trim());
        } 
      }
      
      if(getHeaders().containsKey("Content-Length")) {
      		String contentLengthString = getHeaders().get("Content-Length");
      		int contentLength = Integer.parseInt(contentLengthString);
      		for(int i = 0; i < contentLength; i++) {
      			body += (char) bufferedReader.read();
      		}
      }
      
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Response isBadRequest() throws IOException{

    if(!getVerb().equals("GET") && 
        !getVerb().equals("PUT") && 
        !getVerb().equals("DELETE") && 
        !getVerb().equals("POST") && 
        !getVerb().equals("HEAD") ){

      return new ResponseBadRequest();

    }

    return null;

  }
  
  public String getVerb() {
    return verb;
  }

  public void setVerb(String verb) {
    this.verb = verb;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getHttpVersion() {
    return httpVersion;
  }

  public void setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
  }

  public Map<String,String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String,String> headers) {
    this.headers = headers;
  }

  public String getRequestLine() {
    return requestLine;
  }

  public void setRequestLine(String requestLine) {
    this.requestLine = requestLine;
  }
}
