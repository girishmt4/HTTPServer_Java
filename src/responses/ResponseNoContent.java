package responses;

import root.Response;

public class ResponseNoContent extends Response{
  public ResponseNoContent() {
    super.setResponseCode(204);
    super.setReasonPhrase("No Content");
  }

}
