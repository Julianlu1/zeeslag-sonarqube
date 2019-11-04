package communicatorserver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import seabattlecommunicatorshared.CommunicatorWebSocketMessage;
import seabattlecommunicatorshared.CommunicatorWebSocketMessageOperation;
import seabattlecommunicatorshared.PlaceShipDTO;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;


@ServerEndpoint(value="/communicator/")
public class CommunicatorServerWebSocket {
    private static final List<Session> sessions = new ArrayList<>();

    // Toevoegen aan sessions lijst
    @OnOpen
    public void onConnect(Session session){
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());
        String message = String.format("[New client with client side session ID]: %s",session.getId());
        sessions.add(session);
        System.out.println("[#sessions]: " + sessions.size());
    }

    // Wanneer een bericht naar de server wordt gestuurd komt de code hier
    @OnMessage
    public void onText(String message, Session session){
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }
    private void handleMessageFromClient(String jsonMessage, Session session) {
        Gson gson = new Gson();

        CommunicatorWebSocketMessage wbMessage = null;


        try {
            wbMessage = gson.fromJson(jsonMessage,seabattlecommunicatorshared.CommunicatorWebSocketMessage.class);
        }
        catch (JsonSyntaxException ex) {
            System.out.println("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }

        // Operation defined in message
        seabattlecommunicatorshared.CommunicatorWebSocketMessageOperation operation;
        operation = wbMessage.getOperation();

        // Process message based on operation
        //String property = wbMessage.getProperty();

        switch(operation){
            case PLACESHIP:
                for(Session s : sessions){
                    PlaceShipDTO placeShipDTO = wbMessage.getPlaceShipDTO();
                    seabattlecommunicatorshared.CommunicatorWebSocketMessage communicatorWebSocketMessage = new seabattlecommunicatorshared.CommunicatorWebSocketMessage(CommunicatorWebSocketMessageOperation.PLACESHIP,placeShipDTO);
                    String message = gson.toJson(communicatorWebSocketMessage);
                    s.getAsyncRemote().sendText(message);
                }
        }
    }
}
