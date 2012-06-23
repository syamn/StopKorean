package syam.StopKorean;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Herochat;

public class HerochatListener implements Listener {
	public final static Logger log = StopKorean.log;
	private static final String logPrefix = StopKorean.logPrefix;
	private static final String msgPrefix = StopKorean.msgPrefix;

	private final StopKorean plugin;
	private Herochat herochat;

	public HerochatListener(final StopKorean plugin){
		this.plugin = plugin;
		this.herochat = this.plugin.herochat;
	}

	/* 登録するイベントはここから下に */

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onHeroChatMessage(ChannelChatEvent event){
		// 発言が無効な場合は何もしない
		if (event.getResult() != Chatter.Result.ALLOWED){
			return;
		}

		// 発言先チャンネル
		String cname = event.getChannel().getName();

		// 発言先チャットが設定ファイルに記載されている場合
		if (plugin.getConfigs().hcChannels.contains(cname)){
			String message = event.getBukkitEvent().getMessage();
			// 正規表現宣言
			Pattern p = Pattern.compile(plugin.regex);
			Matcher m = p.matcher(message);
			// 正規表現に一致
			if (m.find()){
				Player player = event.getBukkitEvent().getPlayer();
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
					log.info(logPrefix+"[Ch: "+cname+"]"+player.getName()+": "+message);
				}

				// CancelEvent
				if (plugin.getConfigs().cancelEvent){
					event.getBukkitEvent().setCancelled(true);
				}
			}
		}
	}
}
