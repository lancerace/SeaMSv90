package client.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.commands.ICommand;
/**
 *
 * @author Magikarp 
 * @date 8/4/2021
 */
public class Invincible extends ICommand {

  @Override
  public void execute(MapleClient c, String[] splitted) {

    if (splitted.length > 1) {
      c.getPlayer().dropMessage(6, "Syntax: !godmode");
      return;
    }
    MapleCharacter player = c.getPlayer();
    if (player.isInvincible()) {
      player.setInvincible(false);
      player.dropMessage(6, "Invincibility deactivated.");
    } else {
      player.setInvincible(true);
      player.dropMessage(6, "Invincibility activated.");
    }

  }
}
