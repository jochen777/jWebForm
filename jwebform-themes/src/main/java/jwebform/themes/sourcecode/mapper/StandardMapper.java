package jwebform.themes.sourcecode.mapper;

import jwebform.field.*;
import jwebform.field.structure.FieldType;
import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.Theme;
import jwebform.themes.sourcecode.producer.*;

import java.util.Optional;

public class StandardMapper implements Mapper{

  private final Theme theme;
  
  public StandardMapper(Theme theme) {
    this.theme = theme;
  }
  
  @Override
  public Optional<HTMLProducer> fromElement(FieldType element) {
    if (element instanceof CheckBoxType) {
      return Optional.of(new CheckBoxProducer(theme));
    }
    if (element instanceof TextType) {
      return Optional.of(new TextProducer(theme));
    }
    if (element instanceof TextDateType) {
      return Optional.of(new TextDateProducer(theme, this));
    }
    if (element instanceof SelectType) {
      return Optional.of(new SelectProducer(theme));
    }
    if (element instanceof SubmitType) {
      return Optional.of(new SubmitProducer(theme));
    }
    if (element instanceof TextAreaType) {
      return Optional.of(new TextAreaProducer(theme));
    }
    if (element instanceof NumberType) {
      return Optional.of(new NumberProducer(theme));
    }
    if (element instanceof PasswordType) {
      return Optional.of(new PasswordProducer(theme));
    }
    if (element instanceof UploadType) {
      return Optional.of(new UploadProducer(theme));
    }
    if (element instanceof LabelType) {
      return Optional.of(new LabelProducer(theme));
    }
    if (element instanceof HiddenType) {
      return Optional.of(new HiddenProducer(theme));
    }
    
    
    if (element instanceof RadioType) {
      return Optional.of(new RadioProducer(theme));
    }
    
    return Optional.empty();
  }

}
