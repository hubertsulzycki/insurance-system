package org.example.policyservice.adapter;

import org.example.policyservice.model.GetFlightRequest;
import org.example.policyservice.model.GetFlightResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class FlightClient extends WebServiceGatewaySupport {

    public boolean checkFlight(String flightNumber) {
        // 1. Tworzymy obiekt zapytania
        GetFlightRequest request = new GetFlightRequest();
        request.setFlightNumber(flightNumber);

        System.out.println("Wysyłam zapytanie do SOAP o lot: " + flightNumber);

        // 2. Wysyłamy i czekamy na odpowiedź (Marshal -> Send -> Receive -> Unmarshal)
        // Adres Mocka to http://localhost:8081/ws
        GetFlightResponse response = (GetFlightResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        // 3. Zwracamy wynik (czy lot istnieje?)
        return response.isExists();
    }
}