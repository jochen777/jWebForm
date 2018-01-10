package jwebform.transfer;

public class DummyTransferToBean implements TransfererFromFormResult {

  @Override
  public void fillBean(Object o) {
    // this should not be used at all. Use the transferer from the integration project.
    throw new UnsupportedOperationException();
  }

}
