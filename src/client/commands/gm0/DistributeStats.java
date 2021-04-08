package client.commands.gm0;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.commands.ICommand;

public abstract class DistributeStats extends ICommand {

    protected MapleStat stat = null;
    private static int statLim = 30000;

    private void setStat(MapleCharacter player, int amount) {
      switch (stat) {
        case STR:
          player.getStat().setStr((short) amount);
          player.updateSingleStat(MapleStat.STR, player.getStat().getStr());
          break;
        case DEX:
          player.getStat().setDex((short) amount);
          player.updateSingleStat(MapleStat.DEX, player.getStat().getDex());
          break;
        case INT:
          player.getStat().setInt((short) amount);
          player.updateSingleStat(MapleStat.INT, player.getStat().getInt());
          break;
        case LUK:
          player.getStat().setLuk((short) amount);
          player.updateSingleStat(MapleStat.LUK, player.getStat().getLuk());
          break;
      }
    }

    private int getStat(MapleCharacter player) {
      switch (stat) {
        case STR:
          return player.getStat().getStr();
        case DEX:
          return player.getStat().getDex();
        case INT:
          return player.getStat().getInt();
        case LUK:
          return player.getStat().getLuk();
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
      if (getStat(c.getPlayer()) + change > statLim) {
        c.getPlayer().dropMessage(5, "The stat limit is " + statLim + ".");
        return;
      }
      setStat(c.getPlayer(), getStat(c.getPlayer()) + change);
      c.getPlayer().setRemainingAp((c.getPlayer().getRemainingAp() - change));
      c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, Math.min(199, c.getPlayer().getRemainingAp()));
      c.getPlayer().dropMessage(5, "You've " + c.getPlayer().getRemainingAp() + " remaining ability points.");
    }
  }