package client.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.PlayerStats;
import client.commands.ICommand;
/**
 *
 * @author Magikarp 
 * @date 8/4/2021
 */
public class Level extends ICommand {

    @Override
    public void execute(MapleClient c, String[] params) {
        int level = 0;
        try {
            level = Integer.parseInt(params[0]);
            if (params.length < 1) {
                c.getPlayer().dropMessage(5, "Invalid number entered.");
                c.getPlayer().dropMessage(5, "Syntax: !level <newlevel>");
                return;
            }
        } catch (NumberFormatException nfe) {
            c.getPlayer().dropMessage(5, "Invalid number entered.");
            return;
        }

        if (level <= 0) {
            c.getPlayer().dropMessage(5, "You must enter a number greater than 0.");
            return;
        }

        if (level < c.getPlayer().getLevel())
            resetCharacterStat(c.getPlayer(), c);
        else
           level -= c.getPlayer().getLevel();
        while (level != 0) {
            c.getPlayer().levelUp(false);
            level--;
        }
    }

    public void resetCharacterStat(MapleCharacter c, MapleClient client) {
        MapleCharacter temp = MapleCharacter.getDefault(client, 1); // adventure = 1
        c.setLevel((short) 1);
        c.resetStatsByJob(false);
        c.setRemainingAp(0);
        c.setRemainingSp(0);
        c.changeJob(c.getJob()); // add back job advance extra hp mp stats
        c.setStats((byte) 5, (short) temp.getStats().hp); // set hp
        c.setStats((byte) 6, (short) temp.getStats().mp); // set mp
        c.setStats((byte) 7, (short) temp.getStats().maxhp); // set mp
        c.setStats((byte) 8, (short) temp.getStats().maxmp); // set mp
    }
}


