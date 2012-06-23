package syam.StopKorean;

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
	private final HerochatListener herochatListener = new HerochatListener(this);
	private final DefaultChatListener defaultchatListener = new DefaultChatListener(this);

	// Private classes
	private ConfigurationManager config;

	// Instance
	private static StopKorean instance;

	// Hookup plugins
	public Herochat herochat;
	public ChannelManager channelmgr;

	// Configs
	public final static String regex = "[\\x{1100}-\\x{11f9}\\x{3131}-\\x{318e}\\x{ac00}-\\x{d7a3}]";// 発言禁止フィルタ ハングルのUnicode指定

	/**
	 * プラグイン起動処理
	 */
	public void onEnable(){
		instance = this;
		config = new ConfigurationManager(this);
		PluginManager pm = getServer().getPluginManager();

		// 設定読み込み
		try{
			config.loadConfig();
		}catch(Exception ex){
			log.warning(logPrefix+ "an error occured while trying to load the config file.");
			ex.printStackTrace();
		}

		// プラグインフック
		if (config.useHerochat && setupHerochat(pm)){
			log.info(logPrefix+"Hooked to Herochat successfully!");
		}else{
			// Herochatを使わない通常のリスナー登録
			pm.registerEvents(defaultchatListener, this);
		}

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
	 * Herochatプラグインにフックする
	 * @param pm PluginManager
	 * @return 成功ならtrue、失敗ならfalse
	 */
	private boolean setupHerochat(final PluginManager pm){
		Plugin p = pm.getPlugin("Herochat");
		if (p == null) p = pm.getPlugin("herochat");
		if (p == null){
			log.warning(logPrefix+"Cannot find Herochat! Will be working without this!");
			return false;
		}
		this.herochat = ((Herochat)p);
		this.channelmgr = Herochat.getChannelManager();
		if (this.channelmgr == null){
			log.warning("Cannot find Herochat channel manager! Will be working without Herochat!");
			return false;
		}

		// イベントを登録
		pm.registerEvents(herochatListener, this);
		return true;
	}

	/**
	 * 設定マネージャを返す
	 * @return ConfigurationManager
	 */
	public ConfigurationManager getConfigs(){
		return config;
	}

	/**
	 * シングルトンパターンでない/ プラグインがアンロードされたイベントでnullを返す
	 * @return シングルトンインスタンス 何もない場合はnull
	 */
	public static StopKorean getInstance() {
    	return instance;
    }
}
