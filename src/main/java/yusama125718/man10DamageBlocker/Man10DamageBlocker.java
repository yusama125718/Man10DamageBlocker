package yusama125718.man10DamageBlocker;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Man10DamageBlocker extends JavaPlugin implements CommandExecutor, Listener {

    static JavaPlugin mdb;
    static Boolean system = false;
    static Boolean entity_cancel = false;
    static Boolean block_cancel = false;
    // 0 -> whitelist
    // 1 -> blacklist
    static Integer mode = 0;
    static List<String> blocks = new ArrayList<>();

    @Override
    public void onEnable() {
        mdb = this;
        mdb.getServer().getPluginManager().registerEvents(this, mdb);
        mdb.saveDefaultConfig();
        system = mdb.getConfig().getBoolean("system");
        entity_cancel = mdb.getConfig().getBoolean("entity_cancel");
        block_cancel = mdb.getConfig().getBoolean("block_cancel");
        mode = mdb.getConfig().getInt("mode");
        blocks = mdb.getConfig().getStringList("blocks");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(!sender.hasPermission("mdblocker.op")) return true;
        if (args.length == 0 || (args.length == 1 && args[0].equals("help"))){
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker [on/off] : システムを[on/off]します"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker　entiity [on/off] : エンティティからのダメージをキャンセルするか設定します"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker block [on/off] : ブロックからのダメージをキャンセルするか決めます"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker mode [blacklist/whitelist] : ブロックからのダメージをブラックリストかホワイトリストか設定します"));
            sender.sendMessage(Component.text("ブラックリスト：リストに入っていたらダメージ保護から除外します"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker addblock [Material] : リストにブロックを追加します"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker remblock [Material] : リストからブロックを削除します"));
            sender.sendMessage(Component.text("[MDBlocker] /mdblocker blocklist : ブロックのリストを表示します"));
            return true;
        }
        else if (args.length == 1){
            if (args[0].equals("on")){
                if (system){
                    sender.sendMessage(Component.text("[MDBlocker] すでにonになっています"));
                    return true;
                }
                system = true;
                mdb.getConfig().set("system", system);
                mdb.saveConfig();
                sender.sendMessage(Component.text("[MDBlocker] onになりました"));
                return true;
            }
            else if (args[0].equals("off")){
                if (!system){
                    sender.sendMessage(Component.text("[MDBlocker] すでにoffになっています"));
                    return true;
                }
                system = false;
                mdb.getConfig().set("system", system);
                mdb.saveConfig();
                sender.sendMessage(Component.text("[MDBlocker] offになりました"));
                return true;
            }
            else if (args[0].equals("blocklist")){
                StringBuilder list = new StringBuilder();
                for (String str: blocks) list.append(str).append(", ");
                if (mode == 0) sender.sendMessage(Component.text("[MDBlocker] 現在のモード：WhiteList"));
                else sender.sendMessage(Component.text("[MDBlocker] 現在のモード：BlackList"));
                sender.sendMessage(Component.text("[MDBlocker] リストに入っているマテリアル一覧"));
                sender.sendMessage(Component.text(list.toString()));
                return true;
            }
        }
        else if (args.length == 2){
            if (args[0].equals("entity")){
                if (args[1].equals("on")){
                    if (entity_cancel){
                        sender.sendMessage(Component.text("[MDBlocker] すでにonになっています"));
                        return true;
                    }
                    entity_cancel = true;
                    mdb.getConfig().set("entity_cancel", entity_cancel);
                    mdb.saveConfig();
                    sender.sendMessage(Component.text("[MDBlocker] onになりました"));
                    return true;
                }
                else if (args[1].equals("off")){
                    if (!entity_cancel){
                        sender.sendMessage(Component.text("[MDBlocker] すでにoffになっています"));
                        return true;
                    }
                    entity_cancel = false;
                    mdb.getConfig().set("entity_cancel", entity_cancel);
                    mdb.saveConfig();
                    sender.sendMessage(Component.text("[MDBlocker] offになりました"));
                    return true;
                }
            }
            else if (args[0].equals("block")){
                if (args[1].equals("on")){
                    if (block_cancel){
                        sender.sendMessage(Component.text("[MDBlocker] すでにonになっています"));
                        return true;
                    }
                    block_cancel = true;
                    mdb.getConfig().set("block_cancel", block_cancel);
                    mdb.saveConfig();
                    sender.sendMessage(Component.text("[MDBlocker] onになりました"));
                    return true;
                }
                else if (args[1].equals("off")){
                    if (!block_cancel){
                        sender.sendMessage(Component.text("[MDBlocker] すでにoffになっています"));
                        return true;
                    }
                    entity_cancel = false;
                    mdb.getConfig().set("block_cancel", block_cancel);
                    mdb.saveConfig();
                    sender.sendMessage(Component.text("[MDBlocker] offになりました"));
                    return true;
                }
            }
            else if (args[0].equals("mode")){
                if (args[1].equals("whitelist")){
                    if (mode == 0){
                        sender.sendMessage(Component.text("[MDBlocker] すでにwhitelistになっています"));
                        return true;
                    }
                    mode = 0;
                    mdb.getConfig().set("mode", mode);
                    mdb.saveConfig();sender.sendMessage(Component.text("[MDBlocker] whitelistになりました"));
                    return true;
                }
                else if (args[1].equals("blacklist")){
                    if (mode == 1){
                        sender.sendMessage(Component.text("[MDBlocker] すでにblacklistになっています"));
                        return true;
                    }
                    mode = 1;
                    mdb.getConfig().set("mode", mode);
                    mdb.saveConfig();
                    sender.sendMessage(Component.text("[MDBlocker] blacklistになりました"));
                    return true;
                }
                else {
                    sender.sendMessage(Component.text("[MDBlocker] /mdblocker mode [blacklist/whitelist] : ブロックからのダメージをブラックリストかホワイトリストか設定します"));
                    return true;
                }
            }
            else if (args[0].equals("addblock")){
                if (blocks.contains(args[1])){
                    sender.sendMessage(Component.text("[MDBlocker] すでに登録されています"));
                    return true;
                }
                if (Material.getMaterial(args[1]) == null){
                    sender.sendMessage(Component.text("[MDBlocker] マテリアルが存在しません"));
                    return true;
                }
                blocks.add(args[1]);
                mdb.getConfig().set("blocks", blocks);
                mdb.saveConfig();
                sender.sendMessage(Component.text("[MDBlocker] 追加しました"));
                return true;
            }
            else if (args[0].equals("remblock")){
                if (!blocks.contains(args[1])){
                    sender.sendMessage(Component.text("[MDBlocker] 登録されていません"));
                    return true;
                }
                blocks.remove(args[1]);
                mdb.getConfig().set("blocks", blocks);
                mdb.saveConfig();
                sender.sendMessage(Component.text("[MDBlocker] 削除しました"));
                return true;
            }
        }
        else sender.sendMessage(Component.text("[MDBlocker] /mbcanceler help でhelpを表示"));
        return true;
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        if (system && entity_cancel) e.setCancelled(true);
    }

    @EventHandler
    public void EntityDamageByBlockEvent(EntityDamageByBlockEvent e){
        if (!system || !block_cancel) return;
        if (mode == 0 && blocks.contains(e.getDamager().getType().toString())) e.setCancelled(true);
        else if (mode == 1 && !blocks.contains(e.getDamager().getType().toString())) e.setCancelled(true);
    }
}
