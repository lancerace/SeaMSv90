/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.messages.commands;

import client.MapleClient;
import client.SkillFactory;
import constants.ServerConstants.PlayerRank;

/**
 * @author Emilyx3
 */
public class GMCommand {

  public static PlayerRank getPlayerLevelRequired() {
    return PlayerRank.IS_GM;
  }

  public static class Hide extends CommandExecute {

    @Override
    public int execute(MapleClient c, String[] splitted) {
      SkillFactory.getSkill(9001004).getEffect(1).applyTo(c.getPlayer());
      return 0;
    }
  }
}
