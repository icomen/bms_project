package it.unicas.bms_project;

import com.opencsv.bean.CsvBindByName;

public class BmsData {
/*
    private Double vcell1;
    private Double vstack;
    private Double temp1;
    private Double soc;

 */
    @CsvBindByName(column = "Vcell1")
    private Double Vcell1;

    @CsvBindByName(column = "Vstack")
    private String Vstack;

    @CsvBindByName(column = "Temp1")
    private Double Temp1;
    @CsvBindByName(column = "Soc")
    private String Soc;

    /*
   @CsvBindAndJoinByName(column = "Vcell*", elementType = String.class)
   private MultiValueMap<String, String> Vcell;

    @CsvBindAndJoinByName(column = "Temp*", elementType = String.class)
    private MultiValueMap<String, String> Temp;

    @CsvBindAndJoinByName(column = ".*", elementType = String.class)
    private MultiValueMap<String, String> TheRest;

*/

    public BmsData() {

    }

    public double getVcell1() {
        return Vcell1;
    }
    public void setVcell1(Double Vcell1) {
        this.Vcell1 = Vcell1;
    }

    public String getVstack() {
        return Vstack;
    }
    public void setVstack(String Vstack) {
        this.Vstack = Vstack;
    }
    public double getTemp1() {
        return Temp1;
    }
    public void setTemp1(Double Temp1) {
        this.Temp1 = Temp1;
    }
    public String getSoc() {
        return Soc;
    }
    public void setSoc(String Soc) {
        this.Soc = Soc;
    }


}
