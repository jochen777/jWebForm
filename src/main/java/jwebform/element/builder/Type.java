package jwebform.element.builder;

import jwebform.element.*;

import java.time.LocalDate;
import java.util.List;

// central builder class to allow simple building of all build-in types.
public class Type {

  private Type() {
    // hide public constructor
  }

  public static TypeBuilder text(String name, String initialValue) {
    return new TypeBuilder().withTypeSupplier(() -> new TextType(name, initialValue));
  }

  public static TypeBuilder text(String name) {
    return text(name, "");
  }

  public static TypeBuilder simple() {
    return new TypeBuilder().withTypeSupplier(() -> new SimpleType());
  }


  public static TypeBuilder checkbox(String name, boolean initialValue) {
    return new TypeBuilder().withTypeSupplier(() -> new CheckBoxType(name, initialValue));
  }

  public static TypeBuilder checkbox(String name) {
    return checkbox(name, false);
  }

  public static TypeBuilder hidden(String name, String initialValue) {
    return new TypeBuilder().withTypeSupplier(() -> new HiddenType(name, initialValue));
  }

  public static TypeBuilder html(String html) {
    return new TypeBuilder().withTypeSupplier(() -> new HtmlType(html));
  }

  public static TypeBuilder label(String label) {
    return new TypeBuilder().withTypeSupplier(() -> new LabelType(label));
  }

  public static TypeBuilder number(String name, int initialValue) {
    return new TypeBuilder().withTypeSupplier(() -> new NumberType(name, initialValue));
  }

  public static TypeBuilder number(String name) {
    return number(name, 0);
  }

    public static TypeBuilder password(String name) {
    return new TypeBuilder().withTypeSupplier(() -> new PasswordType(name));
  }

  public static TypeBuilder radio(String name, String initialValue, String keys[],
      String values[]) {
    return new TypeBuilder()
        .withTypeSupplier(() -> new RadioType(name, initialValue, keys, values));
  }

  public static TypeBuilder submit(String name) {
    return new TypeBuilder()
      .withTypeSupplier(() -> new SubmitType(name));
  }

  public static TypeBuilder submit() {
    return new TypeBuilder()
      .withTypeSupplier(() -> new SubmitType("submit"));
  }


  public static TypeBuilder textArea(String name, String initialValue) {
    return new TypeBuilder().withTypeSupplier(() -> new TextAreaType(name, initialValue));
  }

  public static TypeBuilder textArea(String name) {
    return textArea(name, "");
  }

  public static TypeBuilder upload(String name) {
    return new TypeBuilder().withTypeSupplier(() -> new UploadType(name));
  }

  public static TypeBuilder xsrfProtection(){
    return new TypeBuilder().withTypeSupplier(() -> new XSRFProtectionType());
  }

  public static TypeBuilder xsrfProtectionForTesting(){
    return new TypeBuilder().withTypeSupplier(() -> new XSRFProtectionType(true));
  }

  // more complex ones

  public static TypeBuilder select(String name, List<SelectType.SelectInputEntryGroup> groups, String initialValue){
    return new TypeBuilder().withTypeSupplier(() -> new SelectType(name, groups, initialValue));
  }

  public static TypeBuilder select(String name, String initialValue, List<SelectType.SelectInputEntry> entries){
    return new TypeBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, entries));
  }

  public static TypeBuilder select(String name, String initialValue, String keys[], String values[]){
    return new TypeBuilder().withTypeSupplier(() -> new SelectType(name,  initialValue, keys, values));
  }

  // dates
  public static TypeBuilder selectDate(String name, LocalDate initialValue, int yearStart, int yearEnd){
    return new TypeBuilder().withTypeSupplier(() -> new SelectDateType(name,  initialValue, yearStart, yearEnd));
  }

  public static TypeBuilder textDate(String name, LocalDate initialValue){
    return new TypeBuilder().withTypeSupplier(() -> new TextDateType(name,  initialValue));
  }


}
