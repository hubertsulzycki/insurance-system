package org.example.soapmock.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "flightNumber" })
@XmlRootElement(name = "GetFlightRequest", namespace = "http://insurance.com/soap-mock/flights")
public class GetFlightRequest {

    @XmlElement(namespace = "http://insurance.com/soap-mock/flights", required = true)
    protected String flightNumber;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }
}
