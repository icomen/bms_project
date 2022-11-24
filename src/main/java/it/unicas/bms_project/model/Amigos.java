package it.unicas.bms_project.model;

import it.unicas.bms_project.MyChangeListener;
import it.unicas.bms_project.util.DateUtil;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for a Colleghi.
 *
 * @author Mario Molinara
 */
public class Amigos {

    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty telefono;
    private StringProperty email;
    private ObjectProperty<LocalDate> cumpleanos;
    private IntegerProperty idamigos;  //wrapper

    private static String attributoStaticoDiEsempio;

    /**
     * Default constructor.
     */
    public Amigos() {
        this(null, null);
    }

    public Amigos(String nombre, String apellido, String telefono, String email, String cumpleanos, Integer idamigos) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.cumpleanos = new SimpleObjectProperty<>(DateUtil.parse(cumpleanos));
        if (idamigos != null){
            this.idamigos = new SimpleIntegerProperty(idamigos);
        } else {
            this.idamigos = null;
        }
    }

    /**
     * Constructor with some initial data.
     *
     * @param nombre
     * @param apellido
     */
    public Amigos(String nombre, String apellido) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        // Some initial dummy data, just for convenient testing.
        this.telefono = new SimpleStringProperty("telefono");
        this.email = new SimpleStringProperty("email@email.com");
        this.cumpleanos = new SimpleObjectProperty<>(DateUtil.parse("24-10-2017"));
        this.idamigos = null;
    }

    public Integer getIdamigos(){
        if (idamigos == null){
            idamigos = new SimpleIntegerProperty(-1);
        }
        return idamigos.get();
    }

    public void setIdamigos(Integer idamigos) {
        if (this.idamigos == null){
            this.idamigos = new SimpleIntegerProperty();
        }
        this.idamigos.set(idamigos);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }
    
    public LocalDate getCumpleanos() {
        return cumpleanos.get();
    }

    public void setCumpleanos(LocalDate cumpleanos) {
        this.cumpleanos.set(cumpleanos);
    }

    public ObjectProperty<LocalDate> cumpleanosProperty() {
        return cumpleanos;
    }


    public String toString(){
        return nombre.getValue() + ", " + apellido.getValue() + ", " + telefono.getValue() + ", " + email.getValue() + ", " + DateUtil.format(cumpleanos.getValue()) + ", (" + idamigos.getValue() + ")";
    }

    public static void pippo(){
        System.out.println("Ciao");
        attributoStaticoDiEsempio = "Mario";
    }


    public static void main(String[] args) {



        // https://en.wikipedia.org/wiki/Observer_pattern
        Amigos collega = new Amigos();
        collega.setNombre("Ciao");
        MyChangeListener myChangeListener = new MyChangeListener();
        collega.nombreProperty().addListener(myChangeListener);
        collega.setNombre("Mario");


        collega.cumpleanosProperty().addListener(myChangeListener);

        collega.cumpleanosProperty().addListener(
                new ChangeListener(){
            @Override
            public void changed(ObservableValue o, Object oldVal,
                                Object newVal){
                System.out.println("Compleanno property has changed!");
            }
        });

        collega.cumpleanosProperty().addListener(
                (o, old, newVal)-> System.out.println("Compleanno property has changed! (Lambda implementation)")
        );


        collega.setCumpleanos(DateUtil.parse("30-10-1971"));



        // Use Java Collections to create the List.
        List<Amigos> list = new ArrayList<>();

        // Now add observability by wrapping it with ObservableList.
        ObservableList<Amigos> observableList = FXCollections.observableList(list);
        observableList.addListener(
          (ListChangeListener) change -> System.out.println("Detected a change! ")
        );

        Amigos c1 = new Amigos();
        Amigos c2 = new Amigos();

        c1.nombreProperty().addListener(
                (o, old, newValue)->System.out.println("Ciao")
        );

        c1.setNombre("Pippo");

        // Changes to the observableList WILL be reported.
        // This line will print out "Detected a change!"
        observableList.add(c1);

        // Changes to the underlying list will NOT be reported
        // Nothing will be printed as a result of the next line.
        observableList.add(c2);


        observableList.get(0).setNombre("Nuovo valore");

        System.out.println("Size: "+observableList.size());

    }


}
