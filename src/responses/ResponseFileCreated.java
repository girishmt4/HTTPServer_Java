package responses;

import java.util.HashMap;
import java.util.Map;

import root.Response;

public class ResponseFileCreated extends Response{
  public ResponseFileCreated(int length) {
    super.setResponseCode(201);
    super.setReasonPhrase("File Created");
    Map<String,String> headers=new HashMap<String,String>();
    headers.put("Content-Length",Integer.toString(length));
    super.setHeaders(headers);
  }

}
