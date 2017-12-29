package root;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpdConf extends ConfigurationReader{
  String serverRoot;
  String documentRoot;
  int listen;
  String logFile;
  Map<String,String> aliases;
  Map<String,String> scriptAliases;
	
  public HttpdConf(String fileName) {
	super(fileName);
	this.aliases=new HashMap<String,String>();
	this.scriptAliases=new HashMap<String,String>();
	this.load();
  }
  
  public void load()
  { 
    try {
      FileReader fileReader=new FileReader("./conf/httpd.conf");
      BufferedReader bufferReader=new BufferedReader(fileReader);
      String thisLine;
      while((thisLine=bufferReader.readLine())!=null){
        String[] elements=thisLine.split(" ");
        if(elements[0].contains("ServerRoot")){
          String serverRootElements[] = elements[1].split("\"");
          this.serverRoot=serverRootElements[1];
        }
        else if(elements[0].contains("DocumentRoot")){
          String documentRootElements[] = elements[1].split("\"");
          this.documentRoot=documentRootElements[1];
        }
        else if(elements[0].contains("Listen")){
          this.listen=Integer.parseInt(elements[1]);
        }
        else if(elements[0].contains("LogFile")){
          String logFileElements[] = elements[1].split("\"");
          this.logFile=logFileElements[1];
        }
        else if(elements[0].contains("ScriptAlias")){
          String scriptAliasElements[] = elements[2].split("\"");
          this.scriptAliases.put(elements[1],scriptAliasElements[1]);
        }
        else if(elements[0].contains("Alias")){
          String aliasElements[] = elements[2].split("\"");
          this.aliases.put(elements[1],aliasElements[1]);
        }
      }
      bufferReader.close();
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
