package it.unicas.bms_project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MyChangeListener implements ChangeListener {

  @Override
  public void changed(ObservableValue observableValue, Object o, Object t1) {
      System.out.println("Evento intercettato dalla classe con nome!");
  }
}
