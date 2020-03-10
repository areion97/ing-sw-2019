package serverSocket.messageManager;

import model.Game;
import model.maps.Square;

/**
 * Deserializes messages sent from client and saves them in order to be processed
 */
public class ServerMessageManager {
    private String value;
    private String object;
    private Square toSquare;

    /**
     * Separates strings divided by comma and saves them in private attributes
     * @param string string to deserialize
     */
    public void deserialize(String string) {

        int separator = string.indexOf(',');

        value = string.substring(0, separator);
        object = string.substring(separator + 1);

    }
    /**
     * Separates x and y coordinates divided by comma and saves them in private attribute toSquare: format   x,y,object
     * @param string move string to deserialize
     */
    public boolean deserializeMoveAction(String string) {
        int firstComma = string.indexOf(',');
        int secondComma = string.indexOf(',', firstComma + 1);

        String x = string.substring(0,firstComma);
        String y =string.substring(firstComma+1,secondComma);

        object = string.substring(secondComma + 1);


        try {
            int xInt = Integer.parseInt(x);
            int yInt = Integer.parseInt(y);

            toSquare = Game.getMap().getSquare(xInt, yInt);


            if (toSquare != null) {
                return true;

            } else {

                return false;
            }
        }catch(Exception e) {
            return false;
        }

    }

    public String getObject() {
        return object;
    }

    public String getValue() {
        return value;
    }

    public Square getToSquare() {
        return toSquare;
    }
}
