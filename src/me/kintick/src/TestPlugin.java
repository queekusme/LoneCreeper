package me.kintick.src;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;;

public class TestPlugin extends JavaPlugin{
	
	public PlayerListener pl = new PlayerListener();
	public Logger _Logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdf = this.getDescription();
		_Logger.info(pdf.getName() + " " + pdf.getVersion() + " Is Now Disabled");
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdf = this.getDescription();
		_Logger.info(pdf.getName() + " " + pdf.getVersion() + " Is Now Enabled");
		PluginManager PM = getServer().getPluginManager();
		PM.registerEvents(this.pl, this);		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){	
		if (sender.isOp() == true){
			if (cmd.getName().equalsIgnoreCase("lcon")){
				if (pl.isGame == false){
					pl.isGame = true;
					pl.creeperLegend = 0;
					pl.playerCreeper = null;
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " Game Restarted By OP");
					if (pl.playercounter > 0){
						pl.onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
					    if (pl.playercounter >= pl.minForGame ){
					    	if (pl.playerCreeper == null){
								pl.chooseNewCreeper(null);
							}
					    }
					}
				}
				else{
					sender.sendMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " You Cannot Turn The Game On When It's Already Running!");
				}
			}else if (cmd.getName().equalsIgnoreCase("lcoff")){
				pl.isGame = false;
				pl.playerCreeper = null;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " Game Disabled By OP");
			}else if (cmd.getName().equalsIgnoreCase("newcreeper")){
				pl.isGame = true;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " OP Forced A New Creeper");
				pl.chooseNewCreeper(null);	
			}else if (cmd.getName().equalsIgnoreCase("close")){
				pl.allowPublic = false;
				for(int x = 0; x < pl.onlinePlayerList.length; x++){
					if (pl.onlinePlayerList[x].isOp() == false){
						pl.onlinePlayerList[x].kickPlayer("OP Closed Server");
					}
				}
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " OP Closed Server");
			}else if (cmd.getName().equalsIgnoreCase("open")){
				pl.allowPublic = true;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " OP Re-Opened Server");
			}else if (cmd.getName().equalsIgnoreCase("creeper")){
				if (pl.playerCreeper != null){
					sender.sendMessage(ChatColor.AQUA + "The Creeper Is: " + ChatColor.GREEN + pl.playerCreeper);
				}else{
					sender.sendMessage(ChatColor.AQUA + "There Is No Current Creeper!");
				}
			}
		}
		else{
			sender.sendMessage(ChatColor.GREEN + "Lone Creeper:" + ChatColor.AQUA + " You Must Be OP To Use That Command!");
		}
		return true;
	}
}
