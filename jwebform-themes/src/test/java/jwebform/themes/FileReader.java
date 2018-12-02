package jwebform.themes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {

  public String loadFileFromClasspath(String name) throws IOException {
    InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
    return getFileContent(in).trim();
  }


  private String getFileContent(InputStream f) throws IOException {
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = f.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    return result.toString("UTF-8");
  }
}
