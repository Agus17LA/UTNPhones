package edu.utn.UTNPhones.projections;

public interface TopTenDestinationsByUser {
//    public Integer getPosition();
    public Integer getTimes();
    public String getDestinationCall();

//    public void setPosition(Integer position);
    public void setTimes(Integer times);
    public void setDestinationCall(String destinationCall);
}
