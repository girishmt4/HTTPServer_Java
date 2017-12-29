package root;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import responses.ResponseFileCreated;
import responses.ResponseInternalServerError;
import responses.ResponseNoContent;
import responses.ResponseNotFound;
import responses.ResponseNotModifiedSince;
import responses.ResponseOK;

public class ResponseFactory {
  
  public static Response getResponse(Request request, Resource resource) throws FileNotFoundException, IOException, ParseException {
    if(request.getVerb().equals("PUT")){
      resource.create(request);
      return new ResponseFileCreated(request.getBody().length());
    }
    
    if(request.getVerb().equals("DELETE")){
      File file=new File(resource.getNewURI());
      if(file.exists()) {
        if(file.delete()) {
          return new ResponseNoContent();
        }
        else {
          return new ResponseInternalServerError();
        }  
      }
      else {
        return new ResponseNotFound(resource);
      }
    }
    
    if(request.getVerb()!="PUT") {
      File file=new File(resource.getNewURI());
      if(file.exists()) {
        return new ResponseOK(resource);
      }
      else
        return new ResponseNotFound(resource);
    }
    if(request.getVerb().equals("GET") && request.getHeaders().containsKey("If-Modified-Since")) {
      return new ResponseNotModifiedSince();
    }
    else if(request.getVerb().equals("HEAD") && !resource.isModified(request)) {
      return new ResponseNotModifiedSince();
    }
    
    return new ResponseOK(resource);
  }

}
