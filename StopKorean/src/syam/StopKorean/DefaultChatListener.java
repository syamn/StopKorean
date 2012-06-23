package syam.StopKorean;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;

public class DefaultChatListener implements Listener {
	public final static Logger log = StopKorean.log;
	private static final String logPrefix = StopKorean.logPrefix;
	private static final String msgPrefix = StopKorean.msgPrefix;

	private final StopKorean plugin;

	public DefaultChatListener(final StopKorean plugin){
		this.plugin = plugin;
	}

	/* 登録するイベントはここから下に */

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerChat(PlayerChatEvent event){
		String message = event.getMessage();

		// 正規表現宣言
		Pattern p = Pattern.compile(plugin.regex);
		Matcher m = p.matcher(message);
		// 正規表現に一致
		if (m.find()){
			Player player = event.getPlayer();
			// Has Permission
			if (player.hasPermission("stopkorean.ignore"))
				return;

			// KickPlayer
			if (plugin.getConfigs().kickPlayer){
				player.kickPlayer(plugin.getConfigs().kickMessage);
			} // WarnPlayer
			else if (plugin.getConfigs().warnPlayer){
				Actions.message(null, player, plugin.getConfigs().warnMessage);
			}

			// LogToConsole
			if (plugin.getConfigs().logToConsole){
				log.info(logPrefix+player.getName()+": "+message);
			}

			// CancelEvent
			if (plugin.getConfigs().cancelEvent){
				event.setCancelled(true);
			}
		}
	}
}
