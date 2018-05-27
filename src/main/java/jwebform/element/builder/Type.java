package jwebform.element.builder;

import jwebform.element.CheckBoxType;
import jwebform.element.HiddenType;
import jwebform.element.HtmlType;
import jwebform.element.LabelType;
import jwebform.element.NumberType;
import jwebform.element.PasswordType;
import jwebform.element.TextType;

public class Type {
  public static TextTypeBuilder text(String name, String initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new TextType(name, initialValue));
  }

  public static TextTypeBuilder checkbox(String name, boolean initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new CheckBoxType(name, initialValue));
  }

  public static TextTypeBuilder hidden(String name, String initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new HiddenType(name, initialValue));
  }

  public static TextTypeBuilder html(String html) {
    return new TextTypeBuilder().withTypeSupplier(() -> new HtmlType(html));
  }

  public static TextTypeBuilder label(String label) {
    return new TextTypeBuilder().withTypeSupplier(() -> new LabelType(label));
  }

  public static TextTypeBuilder number(String name, int initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new NumberType(name, initialValue));
  }

  public static TextTypeBuilder password(String name) {
    return new TextTypeBuilder().withTypeSupplier(() -> new PasswordType(name));
  }

}
