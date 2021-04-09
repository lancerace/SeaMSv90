package client.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.PlayerStats;
import client.commands.ICommand;

public class MaxStats extends ICommand{
 

    @Override
    public void execute(MapleClient c, String[] params) {

        MapleCharacter player = c.getPlayer();
        PlayerStats stats = player.getStats();

        stats.setMaxHp(30000);
        stats.setMaxMp(30000);
        player.updateSingleStat(MapleStat.MAXHP, 30000);
        player.updateSingleStat(MapleStat.MAXMP, 30000);
        stats.setDex(Short.MAX_VALUE);
        stats.setStr(Short.MAX_VALUE);
        stats.setInt(Short.MAX_VALUE);
        stats.setLuk(Short.MAX_VALUE);
        player.updateSingleStat(MapleStat.DEX, Short.MAX_VALUE);
        player.updateSingleStat(MapleStat.STR, Short.MAX_VALUE);
        player.updateSingleStat(MapleStat.INT, Short.MAX_VALUE);
        player.updateSingleStat(MapleStat.LUK, Short.MAX_VALUE);
        stats.setHp(c.getPlayer().getStats().getCurrentMaxHp());
        stats.setMp(c.getPlayer().getStats().getCurrentMaxMp());
        player.updateSingleStat(MapleStat.HP, c.getPlayer().getStats().getCurrentMaxHp());
        player.updateSingleStat(MapleStat.MP, c.getPlayer().getStats().getCurrentMaxMp());
        player.setStats(stats);
        player.dropMessage(6, "Stats maxed out");        
    }
}
