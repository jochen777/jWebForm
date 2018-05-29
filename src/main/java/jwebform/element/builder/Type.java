package jwebform.element.builder;

import jwebform.element.*;

import java.time.LocalDate;
import java.util.List;

// central builder class to allow simple building of all build-in types.
public class Type {
  public static TextTypeBuilder text(String name, String initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new TextType(name, initialValue));
  }

  public static TextTypeBuilder text(String name) {
    return text(name, "");
  }

  public static TextTypeBuilder simple() {
    return new TextTypeBuilder().withTypeSupplier(() -> new SimpleType());
  }


  public static TextTypeBuilder checkbox(String name, boolean initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new CheckBoxType(name, initialValue));
  }

  public static TextTypeBuilder checkbox(String name) {
    return checkbox(name, false);
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

  public static TextTypeBuilder number(String name) {
    return number(name, 0);
  }

    public static TextTypeBuilder password(String name) {
    return new TextTypeBuilder().withTypeSupplier(() -> new PasswordType(name));
  }

  public static TextTypeBuilder radio(String name, String initialValue, String keys[],
      String values[]) {
    return new TextTypeBuilder()
        .withTypeSupplier(() -> new RadioType(name, initialValue, keys, values));
  }

  public static TextTypeBuilder submit(String name) {
    return new TextTypeBuilder()
      .withTypeSupplier(() -> new SubmitType(name));
  }

  public static TextTypeBuilder submit() {
    return new TextTypeBuilder()
      .withTypeSupplier(() -> new SubmitType("submit"));
  }


  public static TextTypeBuilder textArea(String name, String initialValue) {
    return new TextTypeBuilder().withTypeSupplier(() -> new TextAreaType(name, initialValue));
  }

  public static TextTypeBuilder textArea(String name) {
    return textArea(name, "");
  }

  public static TextTypeBuilder upload(String name) {
    return new TextTypeBuilder().withTypeSupplier(() -> new UploadType(name));
  }

  public static TextTypeBuilder xsrfProtection(){
    return new TextTypeBuilder().withTypeSupplier(() -> new XSRFProtectionType());
  }

  public static TextTypeBuilder xsrfProtectionForTesting(){
    return new TextTypeBuilder().withTypeSupplier(() -> new XSRFProtectionType(true));
  }

  // more complex ones

  public static TextTypeBuilder select(String name, List<SelectType.SelectInputEntryGroup> groups, String initialValue){
    return new TextTypeBuilder().withTypeSupplier(() -> new SelectType(name, groups, initialValue));
  }

  public static TextTypeBuilder select(String name, String initialValue, List<SelectType.SelectInputEntry> entries){
    return new TextTypeBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, entries));
  }

  public static TextTypeBuilder select(String name, String initialValue, String keys[], String values[]){
    return new TextTypeBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, keys, values));
  }

  // dates
  public static TextTypeBuilder selectDate(String name, LocalDate initialValue, int yearStart, int yearEnd){
    return new TextTypeBuilder().withTypeSupplier(() -> new SelectDateType(name,  initialValue, yearStart, yearEnd));
  }

  public static TextTypeBuilder textDate(String name, LocalDate initialValue){
    return new TextTypeBuilder().withTypeSupplier(() -> new TextDateType(name,  initialValue));
  }


}
