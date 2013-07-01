package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n._;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.FormatUtil;
import java.util.Locale;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commandrealname extends EssentialsCommand
{
	public Commandrealname()
	{
		super("realname");
	}

	@Override
	protected void run(final Server server, final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		final String whois;
		if (args.length == 0) {
			whois = "";
		} else {
			whois = args[0].toLowerCase(Locale.ENGLISH);
		}
		boolean skipHidden = sender instanceof Player && !ess.getUser(sender).isAuthorized("essentials.vanish.interact");
		boolean foundUser = false;
		for (Player onlinePlayer : server.getOnlinePlayers())
		{
			final User u = ess.getUser(onlinePlayer);
			if (skipHidden && u.isHidden())
			{
				continue;
			}
			u.setDisplayNick();
			final String displayName = FormatUtil.stripFormat(u.getDisplayName()).toLowerCase(Locale.ENGLISH);
			if (displayName.contains(whois))
			{
				foundUser = true;
				sender.sendMessage(u.getDisplayName() + " " + _("is") + " " + u.getName());
			}
		}
		if (!foundUser)
		{
			throw new PlayerNotFoundException();
		}
	}
}
