package jwebform.field.builder;

import java.time.LocalDate;
import java.util.List;
import jwebform.field.CheckBoxType;
import jwebform.field.HiddenType;
import jwebform.field.HtmlType;
import jwebform.field.LabelType;
import jwebform.field.NumberType;
import jwebform.field.PasswordType;
import jwebform.field.RadioType;
import jwebform.field.SelectDateType;
import jwebform.field.SelectType;
import jwebform.field.SimpleType;
import jwebform.field.SubmitType;
import jwebform.field.TextAreaType;
import jwebform.field.TextDateType;
import jwebform.field.TextType;
import jwebform.field.UploadType;
import jwebform.field.XSRFProtectionType;

/**
 *  central builder class to allow simple building of all build-in types.
 *
 *  you can implement a class like this for your own types.
 *
 */
public class BuildInType {

  private BuildInType() {
    // hide public constructor
  }

  // simple Fields

  public static FieldBuilder text(String name, String initialValue) {
    return new FieldBuilder().withTypeSupplier(() -> new TextType(name, initialValue));
  }

  public static FieldBuilder text(String name) {
    return text(name, "");
  }

  public static FieldBuilder simple() {
    return new FieldBuilder().withTypeSupplier(SimpleType::new);
  }

  public static FieldBuilder checkbox(String name, boolean initialValue) {
    return new FieldBuilder().withTypeSupplier(() -> new CheckBoxType(name, initialValue));
  }

  public static FieldBuilder checkbox(String name) {
    return checkbox(name, false);
  }

  public static FieldBuilder hidden(String name, String initialValue) {
    return new FieldBuilder().withTypeSupplier(() -> new HiddenType(name, initialValue));
  }

  public static FieldBuilder html(String html) {
    return new FieldBuilder().withTypeSupplier(() -> new HtmlType(html));
  }

  public static FieldBuilder label(String label) {
    return new FieldBuilder().withTypeSupplier(() -> new LabelType(label));
  }

  public static FieldBuilder number(String name, int initialValue) {
    return new FieldBuilder().withTypeSupplier(() -> new NumberType(name, initialValue));
  }

  public static FieldBuilder number(String name) {
    return number(name, 0);
  }

    public static FieldBuilder password(String name) {
    return new FieldBuilder().withTypeSupplier(() -> new PasswordType(name));
  }

  public static FieldBuilder radio(String name, String initialValue, String [] keys,
      String [] values) {
    return new FieldBuilder()
        .withTypeSupplier(() -> new RadioType(name, initialValue, keys, values));
  }

  public static FieldBuilder submit(String name) {
    return new FieldBuilder()
      .withTypeSupplier(() -> new SubmitType(name));
  }

  public static FieldBuilder submit() {
    return new FieldBuilder()
      .withTypeSupplier(() -> new SubmitType("Submit"));
  }

  public static FieldBuilder textArea(String name, String initialValue) {
    return new FieldBuilder().withTypeSupplier(() -> new TextAreaType(name, initialValue));
  }

  public static FieldBuilder textArea(String name) {
    return textArea(name, "");
  }

  public static FieldBuilder upload(String name) {
    return new FieldBuilder().withTypeSupplier(() -> new UploadType(name));
  }

  public static FieldBuilder xsrfProtection(){
    return new FieldBuilder().withTypeSupplier(XSRFProtectionType::new);
  }

  public static FieldBuilder xsrfProtectionForTesting(){
    return new FieldBuilder().withTypeSupplier(() -> new XSRFProtectionType(true));
  }

  // more complex ones

  public static FieldBuilder select(String name, List<SelectType.SelectInputEntryGroup> groups, String initialValue){
    return new FieldBuilder().withTypeSupplier(() -> new SelectType(name, groups, initialValue));
  }

  public static FieldBuilder select(String name, String initialValue, List<SelectType.SelectInputEntry> entries){
    return new FieldBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, entries));
  }

  public static FieldBuilder select(String name, String initialValue, String [] keys, String [] values){
    return new FieldBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, keys, values));
  }

  // dates
  public static FieldBuilder selectDate(String name, LocalDate initialValue, int yearStart, int yearEnd){
    return new FieldBuilder().withTypeSupplier(() -> new SelectDateType(name,  initialValue, yearStart, yearEnd));
  }

  // without initial value
  public static FieldBuilder selectDate(String name, int yearStart, int yearEnd){
    return new FieldBuilder().withTypeSupplier(() -> new SelectDateType(name,  yearStart, yearEnd));
  }

  public static FieldBuilder textDate(String name, LocalDate initialValue){
    return new FieldBuilder().withTypeSupplier(() -> new TextDateType(name,  initialValue));
  }

  public
  static <T> T[] array(T... values) { return values; }


}
