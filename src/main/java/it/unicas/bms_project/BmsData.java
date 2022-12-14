package it.unicas.bms_project;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;

public class BmsData {


    @CsvBindByName(column = "Vstack")
    private Double Vstack;

    @CsvBindByName(column = "Soc")
    private Double Soc;

    @CsvBindByName(column = "I")
    private Double I;

    @CsvBindByName(column = "W")
    private Double W;

    @CsvBindByName(column = "OT")
    private Double OT;

    @CsvBindByName(column = "UT")
    private Double UT;

    @CsvBindByName(column = "OV")
    private Double OV;

    @CsvBindByName(column = "UV")
    private Double UV;

    @CsvBindByName(column = "A")
    private Double A;


    @CsvBindAndJoinByName(column = "Vcell[0-9]+", elementType = Double.class)
    private MultiValuedMap<String, Double> Vcell;

    @CsvBindAndJoinByName(column = "Temp[0-9]+", elementType = Double.class)
    private MultiValuedMap<String, Double> Temp;

    public BmsData() {

    }

    public Double getOT() {
        return OT;
    }

    public void setOT(Double OT) {
        this.OT = OT;
    }

    public Double getUT() {
        return UT;
    }

    public void setUT(Double UT) {
        this.UT = UT;
    }

    public Double getOV() {
        return OV;
    }

    public void setOV(Double OV) {
        this.OV = OV;
    }

    public Double getUV() {
        return UV;
    }

    public void setUV(Double UV) {
        this.UV = UV;
    }

    public Double getVstack() {
        return Vstack;
    }

    public void setVstack(Double vstack) {
        Vstack = vstack;
    }

    public Double getSoc() {
        return Soc;
    }

    public void setSoc(Double soc) {
        Soc = soc;
    }

    public Double getI() {
        return I;
    }

    public void setI(Double i) {
        I = i;
    }

    public Double getW() {
        return W;
    }

    public void setW(Double w) {
        W = w;
    }

    public Double getA() {
        return A;
    }

    public void setA(Double a) {
        A = a;
    }

    public MultiValuedMap<String, Double> getVcell() {
        return Vcell;
    }

    public void setVcell(MultiValuedMap<String, Double> vcell) {
        Vcell = vcell;
    }

    public MultiValuedMap<String, Double> getTemp() {
        return Temp;
    }

    public void setTemp(MultiValuedMap<String, Double> temp) {
        Temp = temp;
    }

}
