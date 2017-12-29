package responses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import root.Response;

public class ResponseInternalServerError extends Response{
  public ResponseInternalServerError() throws FileNotFoundException, IOException {
    super.setResponseCode(500);
    super.setReasonPhrase("Internal Server Error");
    super.setBody(getContentsFromFile(new File("./response_pages/InternalServerError.html")));
  }

}
