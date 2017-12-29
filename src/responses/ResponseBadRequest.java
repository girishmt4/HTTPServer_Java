package responses;

import java.io.File;
import java.io.IOException;

import root.Response;

public class ResponseBadRequest extends Response{

	public ResponseBadRequest() throws IOException{
		
		super.setResponseCode(400);
		super.setReasonPhrase("Bad Request");
		
		super.getHeaders().put("Content-Type","text/html");
		super.setBody(super.getContentsFromFile(new File("./response_pages/BadRequest.html")));
		
	}

}
