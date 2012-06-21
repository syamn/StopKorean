package syam.StopKorean;

import java.awt.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.dthielke.herochat.ChannelManager;
import com.dthielke.herochat.Herochat;

public class StopKorean extends JavaPlugin{
	// Logger
	public final static Logger log = Logger.getLogger("Minecraft");
	public final static String logPrefix = "[StopKorean] ";
	public final static String msgPrefix = "&c[StopKorean] &f";

	// Listener
	private final ChatListener chatListener = new ChatListener(this);

	// Instance
	private static StopKorean instance;

	// Plugins
	public Herochat herochat;
	public ChannelManager channelmgr;

	// Configs
	public static ArrayList<String> globalsList = new ArrayList<String>();
	public final static String[] globalsArray = {"Global","Local"}; // 発言禁止先チャンネル
	public final static String regex = "[\\x{1100}-\\x{11f9}\\x{3131}-\\x{318e}\\x{ac00}-\\x{d7a3}]";// 発言禁止フィルタ ハングルUnicode指定

	/**
	 * プラグイン起動処理
	 */
	public void onEnable(){
		instance = this;
		PluginManager pm = getServer().getPluginManager();

		// プラグインフック
		Plugin p = pm.getPlugin("Herochat");
		if (p == null){
			log.severe("Cannot find Herochat!");
			return;
		}
		this.herochat = ((Herochat)p);
		this.channelmgr = Herochat.getChannelManager();
		if (this.channelmgr == null){
			log.severe("Cannot find Herochat channel manager!");
			return;
		}

		// 配列をリストに格納
		for (String channel : globalsArray){
			globalsList.add(channel);
		}

		// イベントを登録
		pm.registerEvents(chatListener, this);

		// メッセージ表示
		PluginDescriptionFile pdfFile=this.getDescription();
		log.info("["+pdfFile.getName()+"] version "+pdfFile.getVersion()+" is enabled!");
	}

	/**
	 * プラグイン停止処理
	 */
	public void onDisable(){

		// メッセージ表示
		PluginDescriptionFile pdfFile=this.getDescription();
		log.info("["+pdfFile.getName()+"] version "+pdfFile.getVersion()+" is disabled!");
	}

	/**
	 * シングルトンパターンでない/ プラグインがアンロードされたイベントでnullを返す
	 * @return シングルトンインスタンス 何もない場合はnull
	 */
	public static StopKorean getInstance() {
    	return instance;
    }
}
