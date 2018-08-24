package jwebform;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.ForceFileuploadMethod;
import jwebform.processor.FieldResults;
import jwebform.view.ProducerInfos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class View {

  private final FieldResults elementResults;


  private final String formId;
  private final Method method;
  private final Html5Validation html5Validaiton;



  public View(String formId, FieldResults elementResults, Method method,
      Html5Validation html5Validation) {
    this.formId = formId;
    this.elementResults = elementResults;
    this.method = method;
    this.html5Validaiton = html5Validation;
  }

  public String getFormId() {
    return formId;
  }

  public String getMethod() {
    return method.name();
  }


  public boolean isUploadEnctypeRequired() {
    for (Map.Entry<Field, FieldResult> entry : elementResults) {
      if (entry.getKey().element instanceof ForceFileuploadMethod) {
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
    List<ProducerInfos> elementList = new ArrayList<>();
    Map<String, ProducerInfos> elementMap = new LinkedHashMap<>();
    List<String> names = new ArrayList<>();
    int tabIndex = 1;
    for (Map.Entry<Field, FieldResult> entry : elementResults) {
      FieldResult elementResult = entry.getValue();
      ProducerInfos pi = new ProducerInfos(formId, tabIndex, elementResult, entry.getKey(),
        createProducerInfoChilds(elementResult.getChilds(), tabIndex));
      elementList.add(pi);
      elementMap.put(elementResult.getStaticElementInfo().getName(), pi);
      names.add(elementResult.getStaticElementInfo().getName());
      tabIndex += elementResult.getStaticElementInfo().getTabIndexIncrement();
    }
    return new ProducerInfosContainer(elementMap, elementList, names);

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
      listOfPis.add(new ProducerInfos(formId, tabIndex, elem.getValue(), elem.getKey()));
    }
    return listOfPis;
  }


  /////////////////////////////////////////////------------------

  public enum Method {
    POST, GET
  }


  public enum Html5Validation {
    ON, OFF
  }
}


