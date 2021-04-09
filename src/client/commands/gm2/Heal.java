package client.commands.gm2;

import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;
/**
 *
 * @author Magikarp 
 * @date 8/4/2021
 */
public class Heal extends ICommand{

    
    @Override
    public void execute(MapleClient c, String[] splitted) {
        if(splitted.length > 1){
            c.getPlayer().dropMessage(6, "Syntax: !heal");
            return;
        }
      c.getPlayer().getStats().setHp(c.getPlayer().getStats().getCurrentMaxHp());
      c.getPlayer().getStats().setMp(c.getPlayer().getStats().getCurrentMaxMp());
      c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStats().getCurrentMaxHp());
      c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStats().getCurrentMaxMp());
    }
}
