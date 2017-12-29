package responses;

import root.Response;

public class ResponseNotModifiedSince extends Response{
  public ResponseNotModifiedSince() {
    super.setResponseCode(304);
    super.setReasonPhrase("Resource Not Modified");
  }

}
