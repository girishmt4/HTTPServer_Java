package root;

import java.io.File;

public class ConfigurationReader {
  File file;
  
  public ConfigurationReader(String fileName) {
	file=new File(fileName);
  }
  
  Boolean hasMoreLines() {
	return false;
  }
  
  String nextLine() {
	  return "";
  }
  
  void load() {
	  
  }
  
  public static void main(String[] args) {
		

	}

}
