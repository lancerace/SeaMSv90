


package client.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;
/**
 *
 * @author Magikarp 
 * @date 8/4/2021
 */
public class Kill extends ICommand {

    @Override
    public void execute(MapleClient c, String[] splitted) {
        MapleCharacter player = c.getPlayer();
        if (splitted.length < 2) {
          c.getPlayer().dropMessage(6, "Syntax: !kill <list player names>");
          return;
        }
        MapleCharacter victim = null;
        for (int i = 1; i < splitted.length; i++) {
          try {
            victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[i]);
          } catch (Exception e) {
            c.getPlayer().dropMessage(6, "Player " + splitted[i] + " not found.");
          }
          if (player.allowedToTarget(victim)) {
            victim.getStats().setHp((short) 0);
            victim.getStats().setMp((short) 0);
            victim.updateSingleStat(MapleStat.HP, 0);
            victim.updateSingleStat(MapleStat.MP, 0);
          }
        }        
    }
    
}
