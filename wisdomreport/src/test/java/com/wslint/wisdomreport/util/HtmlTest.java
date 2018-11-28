package com.wslint.wisdomreport.util;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;

public class HtmlTest {

  private static final String PATH =
      "D:/workspace/rebuild/product/1/document/html/htmlTemplate/1/1.htm";

  @Test
  public void htmlTest() {
    File file = new File(PATH);
    InputStreamSource inputStreamSource = new FileSystemResource(file);
    try {
      Document document = Jsoup.parse(inputStreamSource.getInputStream(), "utf-8", "");
      Elements input = document.select("input");
      for (Element element : input) {
        System.out.println(element.attr("id"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
