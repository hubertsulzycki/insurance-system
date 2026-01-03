package org.example.soapmock.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "exists", "details" })
@XmlRootElement(name = "GetFlightResponse", namespace = "http://insurance.com/soap-mock/flights")
public class GetFlightResponse {

    @XmlElement(namespace = "http://insurance.com/soap-mock/flights")
    protected boolean exists;

    @XmlElement(namespace = "http://insurance.com/soap-mock/flights", required = true)
    protected String details;

    public void setExists(boolean value) {
        this.exists = value;
    }

    public void setDetails(String value) {
        this.details = value;
    }
}
