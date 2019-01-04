package jwebform.themes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


// Basic Template processing - just used for testing! Do not use this in production...
public class SimpleTemplate {


  Map<String, String> snips;

  public SimpleTemplate() {
    loadTemplateSnips();
  }


  private void loadTemplateSnips() {
    snips = new HashMap<>();
    File[] files = getResourceFolderFiles("test/snips");
    for (File file : files) {
      snips.put(file.getName(), getFileContent(file));
    }
  }

  private String getFileContent(File f) {
    try {
      return readFile(f).trim();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  static String readFile(File f) throws IOException {
    byte[] encoded = Files.readAllBytes(f.toPath());
    return new String(encoded, Charset.defaultCharset()).trim();
  }




  public String loadAndProcessTempalte(String name) throws IOException {

    FileReader fr = new FileReader();

    return processContent(fr.loadFileFromClasspath(name));
  }

  private String processContent(String fileContent) {
    String finalContent = fileContent;
    for (String snipName : snips.keySet()) {
      finalContent = finalContent.replaceAll("##" + snipName + "##", snips.get(snipName));
    }
    return finalContent;
  }

  private static File[] getResourceFolderFiles(String folder) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(folder);
    String path = url.getPath();
    return new File(path).listFiles();
  }

}
