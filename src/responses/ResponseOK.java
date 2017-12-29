package responses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import root.Resource;
import root.Response;

public class ResponseOK extends Response{
  public ResponseOK(Resource resource) throws FileNotFoundException, IOException {
    super.setResponseCode(200);
    super.setReasonPhrase("OK");
    String extension=resource.getNewURI().substring(resource.getNewURI().lastIndexOf(".")+1);
    super.getHeaders().put("Content-Type",resource.getMimeTypes().findContentType(extension));
    super.getContentsFromFile(new File(resource.getNewURI()));
    super.setBody(super.getContentsFromFile(new File(resource.getNewURI())));
  }

}
