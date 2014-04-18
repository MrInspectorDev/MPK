package com.mrinspector.plugin;

import java.io.IOException;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.ricbyte.api.API;

public class MPK extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
	    getConfig().options().copyDefaults(true);
	    saveDefaultConfig();
	    
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
	    
		if (!setupEconomy()) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			
			return;
		}

	}
	
	public static Economy eco = null;

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		eco = rsp.getProvider();
		return eco != null;
	}
	
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  final Player p = (Player)sender;
	    	if(cmd.getName().equalsIgnoreCase("mpkreload")) {
	            super.reloadConfig();
	            p.sendMessage(ChatColor.GOLD + "[MPK] " + ChatColor.GRAY + "You have reloaded config.");
	            
	    	}
			return false;
	  }
	
	@EventHandler
	public void onJoinMessage(PlayerJoinEvent e){
		if(e.getPlayer().hasPermission("mpk.normal")) {
		String messageonjoin = super.getConfig().getString("MessageOnJoin");
		messageonjoin = ChatColor.translateAlternateColorCodes('&', messageonjoin);
		e.getPlayer().sendMessage(messageonjoin);
		
		if(e.getPlayer().hasPermission("mpk.vip")) {
			String vipmessageonjoin = super.getConfig().getString("VIPMessageOnJoin");
			vipmessageonjoin = ChatColor.translateAlternateColorCodes('&', vipmessageonjoin);
			e.getPlayer().sendMessage(vipmessageonjoin);
		}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.getPlayer().sendMessage(ChatColor.GOLD + "[MPK] " + ChatColor.GREEN + "Running MPK version 3.1.0 by" + ChatColor.GRAY + "MrInspector.");
		if(e.getPlayer().hasPermission("mpk.normal")) {
		String messageonleave = super.getConfig().getString("MessageOnLeave").replace("{name}", e.getPlayer().getName());
		messageonleave = ChatColor.translateAlternateColorCodes('&', messageonleave);
		Bukkit.broadcastMessage(messageonleave);
		
		if(e.getPlayer().hasPermission("mpk.vip")) {
			String vipmessageonleave = super.getConfig().getString("VIPMessageOnLeave");
			vipmessageonleave = ChatColor.translateAlternateColorCodes('&', vipmessageonleave).replace("{name}", e.getPlayer().getName());
			Bukkit.broadcastMessage(vipmessageonleave);
		}
	}
}
	
	@EventHandler
	public void onPvE(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Animals) {
			if(e.getEntity().getKiller() instanceof Player) {
				String entitykillmsg = super.getConfig().getString("EntityKillMessage").replace("{killersname}", e.getEntity().getPlayer().getKiller().getName().replace("{mob}", e.getEntity().getType().toString()));
						
			    double amountonentitykill = super.getConfig().getDouble("AmountOnEntityKill");
										
			    eco.depositPlayer(e.getEntity().getKiller().getName(), amountonentitykill);
				
				e.getEntity().getKiller().sendMessage(entitykillmsg);
			}
				
		}
	}
	
	@EventHandler
	public void onKillAndDeath(PlayerDeathEvent e) {
		if(e.getEntity().getPlayer().getKiller().hasPermission("mpk.normal")){	
		String messageonkill = super.getConfig().getString("MessageOnKill").replace("{killersname}", e.getEntity().getPlayer().getKiller().getName()).replace("{name}", e.getEntity().getPlayer().getName());
		String messageondeath = super.getConfig().getString("MessageOnDeath").replace("%player%", e.getEntity().getPlayer().getName());
		
		double amountonkill = super.getConfig().getDouble("AmountOnKill");
		double amountondeath = super.getConfig().getDouble("AmountOnDeath");
		
		double healthonkill = super.getConfig().getDouble("HealthOnKill");
		double maxhealthonkill = super.getConfig().getDouble("MaxHealthOnKill");
		
		e.getEntity().getPlayer().getKiller().sendMessage(messageonkill);
		e.getEntity().getPlayer().sendMessage(messageondeath);
		API.addHealth(e.getEntity().getPlayer().getKiller(), (int) healthonkill);
		API.setMaxHealth(e.getEntity().getPlayer().getKiller(), (int) maxhealthonkill);
		eco.depositPlayer(e.getEntity().getPlayer().getKiller().getName(), amountonkill);
		eco.withdrawPlayer(e.getEntity().getPlayer().getName(), amountondeath);
		
		if(e.getEntity().getPlayer().getKiller().hasPermission("mpk.vip")){
			String vipmessageonkill = super.getConfig().getString("VIPMessageOnKill").replace("{killersname}", e.getEntity().getPlayer().getKiller().getName()).replace("{name}", e.getEntity().getPlayer().getName());
			String vipmessageondeath = super.getConfig().getString("VIPMessageOnDeath").replace("%player%", e.getEntity().getPlayer().getName());
			
			double vipamountonkill = super.getConfig().getDouble("VIPAmountOnKill");
			double vipamountondeath = super.getConfig().getDouble("VIPAmountOnDeath");
			double viphealthonkill = super.getConfig().getDouble("VIPHealthOnKill");
			double vipmaxhealthonkill = super.getConfig().getDouble("VIPMaxHealthOnKill");
			API.addHealth(e.getEntity().getPlayer().getKiller(), (int) viphealthonkill);
			API.setMaxHealth(e.getEntity().getPlayer().getKiller(), (int) vipmaxhealthonkill);
			e.getEntity().getPlayer().getKiller().sendMessage(vipmessageonkill);
			e.getEntity().getPlayer().sendMessage(vipmessageondeath);
			eco.depositPlayer(e.getEntity().getPlayer().getKiller().getName(), vipamountonkill);
			eco.withdrawPlayer(e.getEntity().getPlayer().getName(), vipamountondeath);
		}
		
		}
	}
}
