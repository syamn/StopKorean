package syam.StopKorean;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopKoreanCommand implements CommandExecutor {
	public final static Logger log = StopKorean.log;
	private static final String logPrefix = StopKorean.logPrefix;
	private static final String msgPrefix = StopKorean.msgPrefix;

	private final StopKorean plugin;
	public StopKoreanCommand(final StopKorean plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		// /stopkorean reload - データ再読み込み
		if (args.length >= 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r"))){
			// 権限チェック
			if (!sender.hasPermission("stopkorean.reload")){
				Actions.message(sender, null, "&cYou don't have permission to use this!");
				return true;
			}
			try{
				plugin.getConfigs().loadConfig(false);
			}catch(Exception ex){
				log.warning(logPrefix+ "an error occured while trying to load the config file.");
				ex.printStackTrace();
				return true;
			}
			Actions.message(sender, null, "&aConfiguration reloaded!");
			return true;
		}

		// Show default messages

		// Herochat status
		String m = "していません。";
		if(plugin.getUsingHerochat())
			m = "しています！";

		Actions.message(sender, null, "&c===================================");
		Actions.message(sender, null, "&bStopKorean Plugin version &3%version &bby syamn");
		Actions.message(sender, null, " &aハングル文字によるスパムを防ぎます！");
		if (sender.hasPermission("stopkorean.reload")){
			Actions.message(sender, null, " /stopkorean reload (/sk r)&7:");
			Actions.message(sender, null, "  &7設定ファイルを再読み込みします");
			Actions.message(sender, null, "  &7注)外部プラグインとの連携設定は反映されません。");
			Actions.message(sender, null, "  &7サーバを再起動するか、/reload コマンドを行ってください。");
			Actions.message(sender, null, "&b 現在Herochatプラグインとリンク"+m);
		}
		Actions.message(sender, null, "&c===================================");

		return true;
	}

}
