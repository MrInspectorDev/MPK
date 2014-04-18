/*
 * API made by RicByte.com
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class API {
	
	/**
	* Broadcast a message with this method: API.debugBroadcast("your debug message here");
	* @param msg
	*/
	public static void debugBroadcast(String msg) {
		Bukkit.broadcastMessage(msg);
	}
	
	/**
	 * Send a message to the console with this method: API.debugConsole("your debug message here");
	 * @param msg
	 */
	public static void debugConsole(String msg) {
		Bukkit.broadcastMessage(msg);
	}
	/**
	 * Send a message to a player.
	 * @param player
	 * @param msg
	 */
	public static void debugMessage(Player player, String msg) {
		player.sendMessage(msg);
	}
	
	/**
	 * Get a players UUID with this method: API.getUUID(player);
	 * @param player
	 */
	public static void getUUID(Player player) {
		player.getPlayer().getUniqueId().toString();
	}
	
	/**
	 * Unfinished method.
	 * @param location
	 */
	public static void getSpawnLocation(Location location) {
		// TODO: Create spawn
	}
	
	/**
	 * Test method, do not use.
	 * @param player
	 * @return 
	 */
	public static Player getPlayer(Player player) {
		Player p = player;
		return p;
		// test method, do not use.
	}
	
	/**
	* Kill the player with this method: API.killerPlayer(player);
	* @param player
	*/
	public static void killPlayer(Player player) {
		player.setHealth(0.0);
	}
	
	/**
	* Get the player's health with this method: API.getHealth(player);
	* @param player
	*/
	public static void getHealth(Player player) {
		((Damageable) player).getHealth();
	}
	
	/**
	 * Add health to the player.
	 * @param player
	 * @param integer
	 */
	public static void addHealth(Player player, int integer) {
		((Damageable) player).setHealth(((Damageable) player).getHealth() + integer);
	}
	
	public static Player getNeariestPlayer(Player player, double x, double y, double z) {
		Player closest = player;
		double distance = 0;
		
		for(Entity e : player.getNearbyEntities(x, y, z)) {
			if(e instanceof Player) {
				Player t = (Player) e;
				
				if(t.getLocation().distance(closest.getLocation()) < distance) {
					distance = t.getLocation().distance(closest.getLocation());
					closest = t;
				}
				
				else continue;
			}
		}
		
		return closest;
	}
	
	/**
	 * Remove a specific amount of health from a person.
	 * @param player
	 * @param integer
	 */
	@SuppressWarnings("deprecation")
	public static void removeHealth(Player player, int integer) {
		((Damageable) player).damage(integer);
	}
	
	/**
	* Method is unfinished.
	* @param location
	*/
	public static void setSpawnLocation(Location location) {
		// TODO: Create spawn
	}
	
	/**
	 * Throw egg method, throw eggs at people.
	 * @param player
	 */
	public static void throwEgg(Player player) {
		player.launchProjectile(Egg.class);
	}
	
	/**
	 * Shoot arrow method, shoot arrows at people.
	 * @param player
	 */
	public static void shootArrow(Player player) {
		player.launchProjectile(Arrow.class);
	}
	
	/**
	 * Throw snowball method, throw snowballs at people.
	 * @param player
	 */
	public static void throwSnowball(Player player) {
		player.launchProjectile(Snowball.class);
	}
	
	/**
	 * Method is unfinished.
	 * @param player
	 */
	public static void ban(Player player) {
		player.setBanned(true);
	}
	
	/**
	 * Method is unfinished.
	 * @param player
	 */
	public static void offlineBan(Player player) {
		// TODO: create method
	}
	
	/**
	 * Mehod is unfinished.
	 * @param player
	 */
	public static void UUIDBan(Player player) {
		// TODO: Create method
	}
	
	/**
	 * Kick all players within the server.
	 * @param player
	 */
	public static void kickAll(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(p.getName());
		}
	}
	
	/**
	 * Ban all players within the server.
	 * @param player
	 */
	public static void banAll(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setBanned(true);
		}
	}
	
	/**
	 * Get the player's IP.
	 * @param player
	 */
	public static void getIP(Player player) {
		
	}
	
	/**
	 * Add a player a permission.
	 * @param perm
	 */
	public void addPermission(Player player, String permisson) {
		//player.addAttachment(this, permisson, true);
	}
	
	/**
	 * Remove a player's permission.
	 * @param perm
	 */
	public static void removePermission(Player player, String permisson) {
		//player.addAttachment((Plugin) this, permisson, false);
	}
	
	/**
	 * Update the player's inventory.
	 * @param player
	 */
	@SuppressWarnings("deprecation")
	public static void updateInventory(Player player) {
		player.updateInventory();
	}
	
	/**
	 * Get the player's max health.
	 * @param player
	 */
	public static void getMaxHealth(Player player) {
		((Damageable) player).getMaxHealth();
	}
	
	/**
	 * Set the player's max health.
	 * @param player
	 * @param integer
	 */
	@SuppressWarnings("deprecation")
	public static void setMaxHealth(Player player, int integer) {
		((Damageable) player).setMaxHealth(integer);
	}
	
	/**
	 * Set the player's health.
	 * @param player
	 * @param integer
	 */
	@SuppressWarnings("deprecation")
	public static void setHealth(Player player, int integer) {
		((Damageable) player).setHealth(integer);
	}
}
