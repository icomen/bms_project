package it.unicas.bms_project;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;

public class BmsData
{


    @CsvBindByName(column = "Vstack")
    private Double Vstack;

    @CsvBindByName(column = "Soc")
    private Double Soc;

    @CsvBindByName(column = "I")
    private Double I;

    @CsvBindByName(column = "W")
    private Double W;

    @CsvBindByName(column = "OT")
    private String OT;

    @CsvBindByName(column = "UT")
    private String UT;

    @CsvBindByName(column = "OV")
    private String OV;

    @CsvBindByName(column = "UV")
    private String UV;

    @CsvBindByName(column = "A")
    private Double A;


    @CsvBindAndJoinByName(column = "Vcell[0-9]+", elementType = Double.class)
    private MultiValuedMap<String, Double> Vcell;

    @CsvBindAndJoinByName(column = "Temp[0-9]+", elementType = Double.class)
    private MultiValuedMap<String, Double> Temp;

    public BmsData()
    {
    }

    public String getOT()
    {
        return OT;
    }

    public void setOT(String OT)
    {
        this.OT = OT;
    }

    public String getUT()
    {
        return UT;
    }

    public void setUT(String UT)
    {
        this.UT = UT;
    }

    public String getOV()
    {
        return OV;
    }

    public void setOV(String OV)
    {
        this.OV = OV;
    }

    public String getUV()
    {
        return UV;
    }

    public void setUV(String UV)
    {
        this.UV = UV;
    }

    public Double getVstack()
    {
        return Vstack;
    }

    public void setVstack(Double vstack)
    {
        Vstack = vstack;
    }

    public Double getSoc()
    {
        return Soc;
    }

    public void setSoc(Double soc)
    {
        Soc = soc;
    }

    public Double getI()
    {
        return I;
    }

    public void setI(Double i)
    {
        I = i;
    }

    public Double getW()
    {
        return W;
    }

    public void setW(Double w)
    {
        W = w;
    }

    public Double getA()
    {
        return A;
    }

    public void setA(Double a)
    {
        A = a;
    }

    public MultiValuedMap<String, Double> getVcell()
    {
        return Vcell;
    }

    public void setVcell(MultiValuedMap<String, Double> vcell)
    {
        Vcell = vcell;
    }

    public MultiValuedMap<String, Double> getTemp()
    {
        return Temp;
    }

    public void setTemp(MultiValuedMap<String, Double> temp)
    {
        Temp = temp;
    }

}
