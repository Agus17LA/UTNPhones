package edu.utn.UTNPhones.projections;

import edu.utn.UTNPhones.domain.City;
import edu.utn.UTNPhones.domain.PhoneLine;

public interface Calls {
    public Integer getOriginCall();
    public Integer getDestinationCall();
    public String getOriginNumberLine();
    public String getDestinationNumberLine();
    public Integer getDuration();
    public String getCallDate();
    public Float getTotalPrice();

    void setOriginCall(Integer OriginCall);
    void setDestinationCall(Integer DestinationCall);
    void setOriginNumberLine(String OriginNumberLine);
    void setDestinationNumberLine(String DestinationNumberLine);
    void setDuration(Integer Duration);
    void setCallDate(String CallDate);
    void setTotalPrice(Float TotalPrice);
}
