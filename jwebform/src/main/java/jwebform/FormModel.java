package jwebform;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.ForceFileuploadMethod;
import jwebform.model.ProducerInfos;
import jwebform.processor.FieldResults;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The FormModel just "enhances" a formResult and is perfect to give informations to your template.
 *
 */
public class FormModel {


  private FormResult formResult;
  private final Method method;
  private final Html5Validation html5Validaiton;



  public FormModel(FormResult formResult, Method method,
      Html5Validation html5Validation) {
    this.formResult = formResult;
    this.method = method;
    this.html5Validaiton = html5Validation;
  }

  public String getFormId() {
    return formResult.getFormId();
  }

  public Method getMethodObject() {
    return method;
  }

  public String getMethod() {
    return method.name();
  }



  public boolean isUploadEnctypeRequired() {
    for (Map.Entry<Field, FieldResult> entry : formResult.getFieldResults()) {
      if (entry.getKey().fieldType instanceof ForceFileuploadMethod) {
        return true;
      }
    }
    return false;
  }

  public boolean isHtml5Validaiton() {
    return html5Validaiton == Html5Validation.ON;
  }

  ////////// For rendering within templates


  public ProducerInfosContainer getProducerInfosContainer() {
    List<ProducerInfos> piList = new ArrayList<>();
    Map<String, ProducerInfos> piMap = new LinkedHashMap<>();
    List<String> names = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<Field, FieldResult> entry : formResult.getFieldResults()) {
      FieldResult fieldResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formResult.getFormId(), tabIndex, fieldResult, entry.getKey(),
        createProducerInfoChilds(fieldResult.getChilds(), tabIndex));
      piList.add(pi);
      piMap.put(fieldResult.getStaticFieldInfo().getName(), pi);
      names.add(fieldResult.getStaticFieldInfo().getName());
      tabIndex += fieldResult.getStaticFieldInfo().getTabIndexIncrement();
    }
    return new ProducerInfosContainer(piMap, piList, names);

  }

  public class ProducerInfosContainer {
    public final Map<String, ProducerInfos> piMap;
    public final List<ProducerInfos> piList;
    public final List<String> names;

    public ProducerInfosContainer(
      Map<String, ProducerInfos> piMap, List<ProducerInfos> piList, List<String> names) {
      this.piMap = piMap;
      this.piList = piList;
      this.names = names;
    }
  }

  private List<ProducerInfos> createProducerInfoChilds(FieldResults childs, int tabIndex) {
    List<ProducerInfos> listOfPis = new ArrayList<>(); // RFE: only, if childs is not empty!
    // RFE: This allows only one depth! It would be cooler, if we can do infinite depth
    for (Entry<Field, FieldResult> elem : childs) {
      listOfPis.add(new ProducerInfos(formResult.getFormId(), tabIndex, elem.getValue(), elem.getKey()));
    }
    return listOfPis;
  }

  public FormResult getFormResult() {
    return formResult;
  }

  public Html5Validation getHtml5Validaiton() {
    return html5Validaiton;
  }

  /////////////////////////////////////////////------------------

  public enum Method {
    POST, GET
  }


  public enum Html5Validation {
    ON, OFF;

    public boolean asBoolean() {
      return this == ON;
    }
  }
}


