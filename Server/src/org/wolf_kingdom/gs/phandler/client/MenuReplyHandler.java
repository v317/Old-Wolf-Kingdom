package org.wolf_kingdom.gs.phandler.client;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.apache.mina.common.IoSession;
import org.wolf_kingdom.gs.connection.Packet;
import org.wolf_kingdom.gs.model.MenuHandler;
import org.wolf_kingdom.gs.model.Player;
import org.wolf_kingdom.gs.model.World;
import org.wolf_kingdom.gs.phandler.PacketHandler;
import org.wolf_kingdom.gs.plugins.PluginHandler;


public class MenuReplyHandler implements PacketHandler {
    /**
     * World instance
     */
    public static final World world = World.getWorld();

    public void handlePacket(Packet p, IoSession session) throws Exception {
		Player player = (Player) session.getAttachment();
		final MenuHandler menuHandler = player.getMenuHandler();
		if (menuHandler == null) {
		    player.setSuspiciousPlayer(true);
		    return;
		}
		final int option = (int) p.readByte();
		final String reply = menuHandler.getOption(option);
		player.resetMenuHandler();
		if (reply == null) {
		    player.setSuspiciousPlayer(true);
		    return;
		}
		FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
		
			@Override
			public Integer call() throws Exception {
				try {
					menuHandler.handleReply(option, reply);
				}
				catch (java.util.ConcurrentModificationException cme) {
					cme.printStackTrace();
				}
			return 1;
				}
			});
			PluginHandler.getPluginHandler().getExecutor().execute(task);
    }
}
