package org.example.soapmock;

import org.example.soapmock.model.GetFlightRequest;
import org.example.soapmock.model.GetFlightResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FlightEndpoint {

    private static final String NAMESPACE_URI = "http://insurance.com/soap-mock/flights";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetFlightRequest")
    @ResponsePayload
    public GetFlightResponse getFlight(@RequestPayload GetFlightRequest request) {
        GetFlightResponse response = new GetFlightResponse();

        if ("LOT123".equals(request.getFlightNumber())) {
            response.setExists(true);
            response.setDetails("Lot Warszawa - Nowy Jork, Godz: 14:00");
        } else {
            response.setExists(false);
            response.setDetails("Nie znaleziono lotu");
        }

        return response;
    }
}