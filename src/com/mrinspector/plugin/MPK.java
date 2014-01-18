package com.mrinspector.plugin;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MPK extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	    getConfig().options().copyDefaults(true);
	    saveDefaultConfig();
		if (!setupEconomy()) {
			getLogger()
					.severe(String
							.format("[%s] - Disabled due to no Vault dependency found!",
									getDescription().getName()));
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
	
	@EventHandler
	public void onJoinMessage(PlayerJoinEvent e){
		String messageonjoin = super.getConfig().getString("MessageOnJoin");
		messageonjoin = ChatColor.translateAlternateColorCodes('&', messageonjoin);
		e.getPlayer().sendMessage(messageonjoin);
		
		@SuppressWarnings("unused")
		boolean disable = super.getConfig().getBoolean("Disable");

		if(super.getConfig().getBoolean("Disable") == true) {
			Bukkit.getPluginManager().disablePlugin(this);
			Bukkit.broadcastMessage(ChatColor.RED + "MPK has been disabled!");
		}
		
		if(super.getConfig().getBoolean("Disable") == false) {
			Bukkit.getPluginManager().enablePlugin(this);
			Bukkit.broadcastMessage(ChatColor.GREEN + "MPK has been enabled!");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		// Do nothing currently
		// may be used for a leave message
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(e.getEntity().getPlayer().getKiller().hasPermission("mpk.receivefunds")){
		String messageonkill = super.getConfig().getString("MessageOnKill");
		String messageondeath = super.getConfig().getString("MessageOnDeath");
		
		double amountonkill = super.getConfig().getDouble("AmountOnKill");
		double amountondeath = super.getConfig().getDouble("AmountOnDeath");
		
		if(messageonkill.contains("{killersname}")) {
			messageonkill.replace("{killersname}", e.getEntity().getPlayer().getKiller().getName());
		}
		
		if(messageonkill.contains("{name}")) {
			messageonkill.replace("{name}", e.getEntity().getPlayer().getName());
		}
		
		if(messageondeath.contains("{killersname}")) {
			messageondeath.replace("{killersname}", e.getEntity().getPlayer().getKiller().getName()); 
		}
		
		if(messageondeath.contains("{name}")) {
			messageondeath.replace("{name}", e.getEntity().getPlayer().getName());
		}
	   				
		messageonkill = ChatColor.translateAlternateColorCodes('&', messageonkill);
		messageondeath = ChatColor.translateAlternateColorCodes('&', messageondeath);
		e.getEntity().getPlayer().getKiller().sendMessage(messageonkill);
		e.getEntity().getPlayer().sendMessage(messageondeath);
		eco.depositPlayer(e.getEntity().getPlayer().getKiller().getName(), amountonkill);
		eco.withdrawPlayer(e.getEntity().getPlayer().getName(), amountondeath);
	}
	}
}
