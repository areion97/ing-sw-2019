package serverSocket.messageManager;

/**
 * This class is used to format the message to send to the client with some informations about weapon chosen to shoot
 */
public class FormatShootingMessage {
      /**
       *
       * @param weaponName weapon chosen
       * @param fireMode name of the interface that the weapon uses for shooting
       * @param numberOfTargets number of targets
       * @param move "" if weapon does not have any movement abilities, otherwise it contains the name of the type of movement
       * @return returns string formatted to send to the player
       */
      public static String format(String weaponName,String fireMode,int numberOfTargets,String move) {
            return weaponName+","+fireMode+","+numberOfTargets+","+move;
      }
}