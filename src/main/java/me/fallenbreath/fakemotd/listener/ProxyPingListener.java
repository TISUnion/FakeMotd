package me.fallenbreath.fakemotd.listener;

import me.fallenbreath.fakemotd.FakeMotd;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener
{
	private FakeMotd fakeMotd;

	public ProxyPingListener(FakeMotd fakeMotd)
	{
		this.fakeMotd = fakeMotd;
	}

	@EventHandler
	public void onPing(ProxyPingEvent e){
		ServerPing response = e.getResponse();
		String description = this.fakeMotd.config.getString("description");
		String version = this.fakeMotd.config.getString("version", response.getVersion().getName());
		int protocol = this.fakeMotd.config.getInt("protocol", response.getVersion().getProtocol());
		int maxPlayer = this.fakeMotd.config.getInt("max_player", response.getPlayers().getMax());
		int playerCount = this.fakeMotd.config.getInt("player_count", response.getPlayers().getOnline());
		ServerPing.PlayerInfo[] sample = response.getPlayers().getSample();

		response.setDescriptionComponent(new TextComponent(description));
		response.setVersion(new ServerPing.Protocol(version, protocol));
		response.setPlayers(new ServerPing.Players(maxPlayer, playerCount, sample));
	}
}
