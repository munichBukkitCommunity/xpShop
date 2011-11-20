package me.ibhh.xpShop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageHandler {
private static YamlConfiguration languageFile;
private static YamlConfiguration defaultLang;

public static boolean setLang(String lang) {

if(lang == null || lang.isEmpty()) {
lang = "en";
}
File defaultLangFile = new File("en.yml");
defaultLang = new YamlConfiguration();
try {
defaultLang.loadFromString(
"ERR_NO_WORLDGUARD: 'Error: WorldGuard was not found.'\n" +
"LOG_EXPIRED_HOTEL: 'Hotel $0 was rent by $1 and just expired'");
} catch (InvalidConfigurationException e) {
outputConsole(Level.SEVERE, "[SimpleRegionMarket] Error: Internal language error!!");
return false;
}

try {
defaultLang.save(defaultLangFile);
} catch (IOException e) {
outputConsole(Level.SEVERE, "[SimpleRegionMarket] Could not save default language 'en.yml'.");
}

File choosenLangFile = new File(lang + ".yml");
boolean ret = false;
if(choosenLangFile.exists()) {
ret = true;
languageFile = YamlConfiguration.loadConfiguration(choosenLangFile);
} else {
languageFile = YamlConfiguration.loadConfiguration(defaultLangFile);
}
return ret;
}

private static String parseLanguageString(String id, ArrayList<String> args) {
String string = id;

if(languageFile != null && languageFile.getString(id) != null) {
string = languageFile.getString(id);
} else if(defaultLang != null && defaultLang.getString(id) != null) {
string = defaultLang.getString(id);
}

for(int i = string.length()-1; i >= 0; i--) {
if(string.charAt(i) == '$') {
if(string.charAt(i-1) == '$') {
string = string.substring(0, i) + string.substring(i+1, string.length());
} else if(Character.isDigit(string.charAt(i+1))) {
int argi;
try {
argi = Integer.parseInt(Character.toString(string.charAt(i+1)));
} catch (Exception e) {
string = string.substring(0, i) + "ERROR ARGUMENT" + string.substring(i+2, string.length());
continue;
}

try {
string = string.substring(0, i) + args.get(argi) + string.substring(i+2, string.length());
} catch (Exception e) {
string = string.substring(0, i) + "ERROR ARGUMENT" + string.substring(i+2, string.length());
continue;
}
}
}
}
return string;
}

public static void outputConsole(Level level, String string) {
Bukkit.getLogger().log(level, "[SimpleRegionMarket] " + string);
}

public static void langOutputConsole(String id, Level level, ArrayList<String> args) {
outputConsole(level, parseLanguageString(id, args));
}

public static void outputDebug(Player p, String id, ArrayList<String> args) {
p.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + "SRM" + ChatColor.WHITE + "] " + ChatColor.YELLOW + parseLanguageString(id, args));
}

public static void outputError(Player p, String id, ArrayList<String> args) {
p.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + "SRM" + ChatColor.WHITE + "] " + ChatColor.RED + parseLanguageString(id, args));
}

public static void outputString(Player p, String string) {
p.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_BLUE + "SRM" + ChatColor.WHITE + "] " + ChatColor.YELLOW + string);
}
}
