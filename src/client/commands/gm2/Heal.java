package client.commands.gm2;

import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;

public class Heal extends ICommand{

    
    @Override
    public void execute(MapleClient c, String[] splitted) {
        if(splitted.length < 0){
            c.getPlayer().dropMessage(6, "Syntax: !heal");
        }
      c.getPlayer().getStats().setHp(c.getPlayer().getStats().getCurrentMaxHp());
      c.getPlayer().getStats().setMp(c.getPlayer().getStats().getCurrentMaxMp());
      c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStats().getCurrentMaxHp());
      c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStats().getCurrentMaxMp());
    }
}
