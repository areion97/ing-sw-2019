package client.messageManager;

public class FormatMessage {

    public static String format(String value,String object) {
        return value+","+object;
    }
    public static String formatMoveAction (String x,String y,String object) {
        String stringModified;
        stringModified=x+","+y+","+object;
        return stringModified;
    }


}
