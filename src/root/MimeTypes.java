package root;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MimeTypes extends ConfigurationReader{
  Map<String,String> types;
  
  public MimeTypes(String fileName) {
	  super("");
	  this.types=new HashMap<String,String>();
  }
  
  public String findContentType (String extension){
    String type = types.get(extension);
    if(type==null){
      return "text/text";
    }
    return types.get(extension);
  }
  
  public void load() {
    try {
      FileReader fileReader = new FileReader("./conf/mime.types");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String thisLine = null;
      while ((thisLine=bufferedReader.readLine())!=null){
        if(thisLine.contains("#") || thisLine.equals("")) {
          continue;
        }
        String[] split = thisLine.split("\\s+");
        for(int i=1;i<split.length;i++){
          this.types.put(split[i], split[0]);
        }
      }
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
