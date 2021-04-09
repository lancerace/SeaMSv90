package client.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;

public class HealAll extends ICommand{

    @Override
    public void execute(MapleClient c, String[] params) {
     
        if(params.length < 0){
            c.getPlayer().dropMessage(6, "Syntax: !healall");
        }

        MapleCharacter player = c.getPlayer();
        for (MapleCharacter mch : player.getMap().getCharacters()) {
          if (mch != null) {
            c.getPlayer().getStats().setHp(c.getPlayer().getStats().getMaxHp());
            c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStats().getMaxHp());
            c.getPlayer().getStats().setMp(c.getPlayer().getStats().getMaxMp());
            c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStats().getMaxMp());
          }
        }
        
    }
    
}
