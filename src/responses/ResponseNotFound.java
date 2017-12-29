package responses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import root.Resource;
import root.Response;

public class ResponseNotFound extends Response{
  public ResponseNotFound(Resource resource) throws FileNotFoundException, IOException {
    super.setResponseCode(404);
    super.setReasonPhrase("Content Not Found");
    super.getHeaders().put("Content-Type",resource.getMimeTypes().findContentType("html"));
    super.setBody(super.getContentsFromFile(new File("./response_pages/NotFound.html")));
  }
}
