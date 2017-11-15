package jwebform.element.structure;

// something, that adds certain behaviour to an element
// TODO: Change this to interface!
public abstract class AbstractBehaviour implements Behaviour {

  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.structure.BehaviourI#getAllAround()
   */
  @Override
  public Wrapper getAllAround() {
    return Wrapper.empty();
  }

}
