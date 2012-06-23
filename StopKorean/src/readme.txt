設定ファイルについて

メッセージ内で日本語を使用はLinuxマシンでUTF-8エンコードで保存すると使用出来ると思いますが、
Windowsマシンはちょっと検証不足です…。

config.yml 日本語説明
-------------------------------------------------
# StopKorean Configuration file for version 1.00
# @author しゃむ <admin@sakura-server.net>
# ハングルを使ったスパムを無効化します
# Minecraftフォーラムスレッド:
# github: https://github.com/syamn/StopKorean

#==================#
#   Basic Config   #
#==================#
# 違反したプレイヤーにメッセージを送ります
# メッセージはカラーコード(&0～&f)を使用可能です
WarnPlayer: false
WarnMessage: "&4[WARNING] Please use Japanese or English!"

# 違反したプレイヤーをKickします
# WarnPlayerがtrueの場合でも、こちらの設定が優先されます
KickPlayer: true
KickMessage: "Please use Japanese or English."

# コンソールに無効化されたハングルメッセージをロギングします
LogToConsole: true

# これをfalseにすると、違反したメッセージが通常の発言としてそのまま表示されるようになります
CancelEvent: true

#=====================#
#   Herochat Config   #
#=====================#
# Herochatプラグインを使う場合trueに変更してください
# この設定を変更した場合、サーバを再起動またはreloadコマンドを実行するまでは反映されません
UseHerochat: false
# UseHerochatがtrueになっている場合、ここで検閲するチャンネルを指定します
HerochatChannnels:
- Global
- Local
- World
-------------------------------------------------

-このプラグインはGPLv3ライセンスで公開します。
-ソースコード(github): https://github.com/syamn/StopKorean
