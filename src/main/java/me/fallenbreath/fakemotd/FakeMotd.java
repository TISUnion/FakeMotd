package me.fallenbreath.fakemotd;

import me.fallenbreath.fakemotd.listener.ProxyPingListener;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.io.File;


public class FakeMotd extends Plugin
{

	public Configuration config;

	@Override
	public void onEnable()
	{
		this.prepareConfig();
		this.getLogger().info(String.format("Description: %s", this.config.getString("description")));
		this.getLogger().info(String.format("Version @ Protocol: %s @ %d", this.config.getString("version"), this.config.getInt("protocol")));
		this.getLogger().info(String.format("Player display: %d/%d", this.config.getInt("player_count"), this.config.getInt("max_player")));
		this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener(this));
	}

	private void prepareConfig()
	{
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		File file = new File(getDataFolder(), "config.yml");

		if (!file.exists())
		{
			try (InputStream in = getResourceAsStream("config.yml"))
			{
				Files.copy(in, file.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
