/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.commands;

import client.MapleClient;
import constants.ServerConstants.CommandType;

public abstract class ICommand {

    protected int rank;
    //protected String description;

    public abstract void execute(MapleClient c, String[] params);

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    protected String joinStringFrom(String arr[], int start) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public CommandType getType() {
        return CommandType.NORMAL;
      }


    public static abstract class TradeExecute extends ICommand {

        @Override
        public CommandType getType() {
          return CommandType.TRADE;
        }
      }
}

