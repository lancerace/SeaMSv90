/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.commands;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import client.MapleClient;
import client.commands.gm0.Dex;
import client.commands.gm0.Int;
import client.commands.gm0.Luk;
import client.commands.gm0.Str;
import client.commands.gm2.Goto;
import client.commands.gm2.Heal;
import client.commands.gm2.HealAll;
import client.commands.gm2.Invincible;
import client.commands.gm2.Kill;
import client.commands.gm2.Level;
import client.commands.gm2.MaxStats;
import constants.ServerConstants.PlayerRank;
import tools.FilePrinter;

/**
 *
 * @author Magikarp 
 * @date 4/4/2021
 */
public class CommandsExecutor {
    public static CommandsExecutor instance = new CommandsExecutor();
    private HashMap<String, ICommand> registeredCommands = new HashMap<>();
    public static CommandsExecutor getInstance() {
        return instance;
    }
    private CommandsExecutor(){
        registerLv0Commands();
        registerLv1Commands();
        registerLv2Commands();
        registerLv3Commands();
    }

    public static boolean isCommand(MapleClient c, String content){
        char heading = content.charAt(0);
        if (c.getPlayer().isGM()){
            return heading == PlayerRank.IS_NORMAL.getCommandPrefix() || heading == PlayerRank.IS_GM.getCommandPrefix();
        }
        return heading == PlayerRank.IS_NORMAL.getCommandPrefix();
    }

    public void handle(MapleClient c, String message) {
        if (c.getPlayer().getMapId() == 300000012 && !c.getPlayer().isGM()) {
            c.getPlayer().dropMessage(5, "You do not have permission to use commands while in jail.");
            return;
        }

        final String splitRegex = "[ ]";
        String[] splitedMessage = message.substring(1).split(splitRegex, 2);
        if (splitedMessage.length < 2) {
            splitedMessage = new String[]{splitedMessage[0], ""};
        }
        
        c.getPlayer().setLastCommandMessage(splitedMessage[1]);    // thanks Tochi & Nulliphite for noticing string messages being marshalled lowercase
        final String commandName = splitedMessage[0].toLowerCase();
        final String[] commandValue = splitedMessage[1].toLowerCase().split(splitRegex);

        final ICommand command = registeredCommands.get(commandName);
        if (command == null){
            c.getPlayer().dropMessage(6, "Command '" + commandName + "' is not available. See @commands for a list of available commands.");
            return;
        }

        if (c.getPlayer().getGMLevel() < command.getRank()){
            c.getPlayer().dropMessage(6, "You do not have permission to use this command.");
            return;
        }

        String[] commandValueClone; //e.g @level 12. commandValueClone only store 12 onward
        if (commandValue.length > 0 && !commandValue[0].isEmpty()) {
            commandValueClone = Arrays.copyOfRange(commandValue, 0, commandValue.length);
        } else {
            commandValueClone = new String[]{};
        }
        
        command.execute(c, commandValueClone);
        writeLog(c, message);

    }

    private void writeLog(MapleClient client, String command){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        FilePrinter.print(FilePrinter.USED_COMMANDS, client.getPlayer().getName() + " used: " + command + " on "
                + sdf.format(Calendar.getInstance().getTime()));
    }

    private void addCommand(String commandSyntax, int rank,  Class<? extends ICommand> commandClass) {
        if (registeredCommands.containsKey(commandSyntax.toLowerCase())){
            System.out.println("Error on register command with name: " + commandSyntax + ". Already exists.");
            return;
        } 
        String commandName = commandSyntax.toLowerCase();
        try {
            ICommand commandInstance = commandClass.newInstance();     // thanks Halcyon for noticing commands getting reinstanced every call
            commandInstance.setRank(rank);
            
            registeredCommands.put(commandName, commandInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void registerLv0Commands(){
        addCommand("luk", PlayerRank.IS_NORMAL.getLevel(), Luk.class);
        addCommand("dex", PlayerRank.IS_NORMAL.getLevel(), Dex.class);
        addCommand("str", PlayerRank.IS_NORMAL.getLevel(), Str.class);
        addCommand("int", PlayerRank.IS_NORMAL.getLevel(), Int.class);
    }

    private void registerLv1Commands(){
        
    }

    private void registerLv2Commands(){
        addCommand("level", PlayerRank.IS_GM.getLevel(),Level.class);
        addCommand("godmode", PlayerRank.IS_GM.getLevel(),Invincible.class);
        addCommand("maxstats", PlayerRank.IS_GM.getLevel(),MaxStats.class);
        addCommand("heal", PlayerRank.IS_GM.getLevel(),Heal.class);
        addCommand("healall", PlayerRank.IS_GM.getLevel(),HealAll.class);
        addCommand("kill",PlayerRank.IS_GM.getLevel(),Kill.class);
        addCommand("goto",PlayerRank.IS_GM.getLevel(),Goto.class);
    }

    private void registerLv3Commands(){
        
    }

}
