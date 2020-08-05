package me.Stellrow.HuntingGrounds.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CUtils {
    public static String asRed(String s){
        return ChatColor.RED+s;
    }
    public static String asGreen(String s){
        return ChatColor.GREEN+s;
    }
    public static String colored(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }
    public static int asInteger(String toTranslate, Player whoSent){
        try {
            return Integer.parseInt(ChatColor.stripColor(toTranslate));
        }catch (IllegalArgumentException ex){
            whoSent.sendMessage(asRed("Not a number!"));
            return 0;
        }

    }
}
