package org.wolf_kingdom.gs.plugins.listeners.executive;

import org.wolf_kingdom.gs.model.Player;

public interface DuelExecutiveListener {
	/**
	 * Return true if you wish to prevent a user from duelling
	 * @param p Player we want to prevent from dueling
	 * @param p2 Other player we want to prevent from dueling
	 */
	public boolean blockDuel(Player p, Player p2);
}
