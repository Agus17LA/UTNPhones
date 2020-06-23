package edu.utn.UTNPhones.projections;

public interface CallOfUser {
    public String getFullNameUserOrigin();
    public String getDniUserOrigin();
    public String getOriginCall();
    public String getFullNameUserDest();
    public String getDniUserDest();
    public String getDestinationCall();
    public String getOriginNumberLine();
    public String getDestinationNumberLine();
    public Integer getInvoice();
    public Integer getDuration();
    public String getCallDate();
    public Float getTotalPrice();

    void setFullNameUserOrigin(String FullNameUserOrigin);
    void setDniUserOrigin(String DniUserOrigin);
    void setOriginCall(String OriginCall);
    void setFullNameUserDest(String FullNameUSerDest);
    void setDniUserDest(String DniUserDest);
    void setDestinationCall(String DestinationCall);
    void setOriginNumberLine(String OriginNumberLine);
    void setDestinationNumberLine(String DestinationNumberLine);
    void setInvoice(Integer Invoice);
    void setDuration(Integer Duration);
    void setCallDate(String CallDate);
    void setTotalPrice(Float TotalPrice);
}
