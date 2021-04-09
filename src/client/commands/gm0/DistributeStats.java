package client.commands.gm0;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;

public abstract class DistributeStats extends ICommand {

    protected MapleStat stats = null;
    private static int statLim = 30000;

    private void setStats(MapleCharacter player, int amount) {
      switch (stats) {
        case STR:
          player.getStats().setStr((short) amount);
          player.updateSingleStat(MapleStat.STR, player.getStats().getStr());
          break;
        case DEX:
          player.getStats().setDex((short) amount);
          player.updateSingleStat(MapleStat.DEX, player.getStats().getDex());
          break;
        case INT:
          player.getStats().setInt((short) amount);
          player.updateSingleStat(MapleStat.INT, player.getStats().getInt());
          break;
        case LUK:
          player.getStats().setLuk((short) amount);
          player.updateSingleStat(MapleStat.LUK, player.getStats().getLuk());
          break;
      }
    }

    private int getStats(MapleCharacter player) {
      switch (stats) {
        case STR:
          return player.getStats().getStr();
        case DEX:
          return player.getStats().getDex();
        case INT:
          return player.getStats().getInt();
        case LUK:
          return player.getStats().getLuk();
        default:
          throw new RuntimeException(); // Will never happen.
      }
    }

    @Override
    public void execute(MapleClient c, String[] splitted) {
      if (splitted.length < 1) {
        c.getPlayer().dropMessage(5, "Invalid number entered.");
        return;
      }
      int change = 0;
      try {
        change = Integer.parseInt(splitted[0]);
      } catch (NumberFormatException nfe) {
        c.getPlayer().dropMessage(5, "Invalid number entered.");
        return;
      }
      if (change <= 0) {
        c.getPlayer().dropMessage(5, "You must enter a number greater than 0.");
        return;
      }
      if (c.getPlayer().getRemainingAp() < change) {
        c.getPlayer().dropMessage(5, "You don't have enough AP for that.");
        return;
      }
      if (getStats(c.getPlayer()) + change > statLim) {
        c.getPlayer().dropMessage(5, "The stat limit is " + statLim + ".");
        return;
      }
      setStats(c.getPlayer(), getStats(c.getPlayer()) + change);
      c.getPlayer().setRemainingAp((c.getPlayer().getRemainingAp() - change));
      c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, Math.min(199, c.getPlayer().getRemainingAp()));
      c.getPlayer().dropMessage(5, "You've " + c.getPlayer().getRemainingAp() + " remaining ability points.");
    }
  }