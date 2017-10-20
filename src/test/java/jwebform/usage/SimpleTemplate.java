package jwebform.usage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Basic Template processing - just used for testing! Do not use this in production...
public class SimpleTemplate {
	
	
	Map<String, String> snips;
	
	public SimpleTemplate() {
		loadTemplateSnips();
	}
	
	
	private void loadTemplateSnips() {
		snips = new HashMap<>();
		File [] files = getResourceFolderFiles("test/snips");
		for (File file : files) {
			snips.put(file.getName(), getFileContent(file ));
		}
	}
	
	private String getFileContent(File f) {
		try {
			return (String)new Scanner( f).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String getFileContent(InputStream f) {
			return (String)new Scanner( f).useDelimiter("\\A").next();
	}

	
	public String loadAndProcessTempalte(String name) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
		String fileContent = getFileContent(in);

		String processedContent = processContent(fileContent);
		return processedContent;
	}
	
	private String processContent(String fileContent) {
		String finalContent = fileContent;
		for(String snipName : snips.keySet()) {
			finalContent = finalContent.replaceAll("##"+snipName+"##", snips.get(snipName));
		}
		// TODO Auto-generated method stub
		return finalContent;
	}

	private static File[] getResourceFolderFiles (String folder) {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    URL url = loader.getResource(folder);
	    String path = url.getPath();
	    return new File(path).listFiles();
	  }

}
