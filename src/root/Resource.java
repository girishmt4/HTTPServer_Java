package root;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Resource {
  private String aliasVariable;
  private String newURI;
  private String uri;
  private HttpdConf httpdConf;
  private MimeTypes mimeTypes;
  
  public Resource(String uri,HttpdConf httpdConf, MimeTypes mimeTypes) {
    this.httpdConf=httpdConf;
    this.uri=uri;
    this.setMimeTypes(mimeTypes);
  }
  
  public boolean isModified(Request request) throws ParseException {
    String headerIfModifiedSince=request.getHeaders().get("If-Modified-Since");
    SimpleDateFormat format=new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss.SSSZ");
    Date dateModifiedSince=format.parse(headerIfModifiedSince);
    Date dateLastModified=new Date(new File(newURI).lastModified());
    if(dateLastModified.after(dateModifiedSince)) {
      return true;
    }
    return false;
  }
  
  public String fullPath() {
    setNewURI(uri);
    for(String thisKey:httpdConf.aliases.keySet()) {
      if(uri.contains(thisKey)) {
        aliasVariable=httpdConf.aliases.get(thisKey);
        setNewURI(getNewURI().replace(thisKey,aliasVariable));
        break;
      }
    }
    
    if(uri==getNewURI()) {
      for(String thisKey:httpdConf.scriptAliases.keySet()) {
        if(uri.contains(thisKey)) {
          aliasVariable=httpdConf.scriptAliases.get(thisKey);
          setNewURI(getNewURI().replace(thisKey,aliasVariable));
        }
      }
    }
    
    if(!getNewURI().contains(httpdConf.documentRoot)) {
      setNewURI(httpdConf.documentRoot+getNewURI());
    }
    
    if(getNewURI().charAt(getNewURI().length()-1)=='/') {
      setNewURI(getNewURI()+"index.html");
    }
    
    return newURI;
    
  }
  
  public boolean isAuthorized(Request request) {
    if(request.getHeaders().containsKey("Authorization")) {
      return true;
    }
    else {
      return false;
    }
  }
  

  public void create(Request request) throws IOException {

    File file = new File(newURI);

    

    if(!file.exists()){
      file.createNewFile();
    }

    FileWriter writer = new FileWriter(newURI);
    BufferedWriter bufferedWriter = new BufferedWriter(writer);
    bufferedWriter.write(request.getBody());
    bufferedWriter.close();

  }

  
  public String getNewURI() {
    return newURI;
  }

  public void setNewURI(String newURI) {
    this.newURI = newURI;
  }

  public MimeTypes getMimeTypes() {
    return mimeTypes;
  }

  public void setMimeTypes(MimeTypes mimeTypes) {
    this.mimeTypes = mimeTypes;
  }

  public void delete(File file) throws IOException {
      file.delete();
  }
    

}
