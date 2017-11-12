package jwebform.element.structure;

// something, that adds certain behaviour to an element
// TODO: Change this to interface!
public abstract class Behaviour {

  public Wrapper getAllAround() {
    return Wrapper.empty();
  }

  public Wrapper wrapLabel(ProducerInfos pi) {
    return Wrapper.empty();
  }
}
