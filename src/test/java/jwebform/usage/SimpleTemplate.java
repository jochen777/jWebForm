package jwebform.usage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
      return readFile(f);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }

  static String readFile(File f) throws IOException {
    byte[] encoded = Files.readAllBytes(f.toPath());
    return new String(encoded, Charset.defaultCharset());
  }

  private String getFileContent(InputStream f) throws IOException {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = f.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    // StandardCharsets.UTF_8.name() > JDK 7
    return result.toString("UTF-8");
  }


  public String loadAndProcessTempalte(String name) throws IOException {
    InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
    String fileContent = getFileContent(in);

    String processedContent = processContent(fileContent);
    return processedContent;
  }

  private String processContent(String fileContent) {
    String finalContent = fileContent;
    for (String snipName : snips.keySet()) {
      finalContent = finalContent.replaceAll("##" + snipName + "##", snips.get(snipName));
    }
    // TODO Auto-generated method stub
    return finalContent;
  }

  private static File[] getResourceFolderFiles(String folder) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(folder);
    String path = url.getPath();
    return new File(path).listFiles();
  }

}
