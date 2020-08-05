package me.Stellrow.HuntingGrounds;

import me.Stellrow.HuntingGrounds.events.HuntingGroundsEvent;
import me.Stellrow.HuntingGrounds.managers.AreaManager;
import me.Stellrow.HuntingGrounds.managers.LootTableManager;
import me.Stellrow.HuntingGrounds.managers.AreaMobManager;
import me.Stellrow.HuntingGrounds.commands.HuntingGroundsCommand;
import me.Stellrow.HuntingGrounds.commands.TabCompletor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class HuntingGrounds extends JavaPlugin {
    //LootTable Manager
    private LootTableManager ltm;
    //Area Manager
    private AreaManager am;
    //AreaMob Manager
    private AreaMobManager amm;
    //areamob.yml
    private File areamob;
    private FileConfiguration areamobcfg;
    //loottable.yml
    private File loottable;
    private FileConfiguration loottablecfg;


    //Change to a better system
    public Location pos1;
    public Location pos2;

    //Sets for objects,keep loadede in memory

    //Creator stick for pos1/pos2
    public ItemStack areaCreator;
    public void onEnable(){
        loadConfig();
        initCommands();
        ltm = new LootTableManager(this);
        amm = new AreaMobManager(this);
        am = new AreaManager(this);

    getServer().getPluginManager().registerEvents(new HuntingGroundsEvent(this),this);
    buildAreaCreator();

    }
    public void onDisable(){
    am.shutDownAreas();
    }
    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
        createloottable();
        createAreamob();
    }
    private void initCommands(){
        getCommand("huntinggrounds").setExecutor(new HuntingGroundsCommand(this));
        getCommand("huntinggrounds").setTabCompleter(new TabCompletor(this));
        getCommand("hg").setExecutor(new HuntingGroundsCommand(this));
        getCommand("hg").setTabCompleter(new TabCompletor(this));
    }
    //Utility
    public AreaManager getAreaManager(){
        return am;
    }
    public LootTableManager getLootTableManager(){
        return ltm;
    }
    public AreaMobManager getAreaMobManager(){
        return amm;
    }
    //Creator
    private void buildAreaCreator(){
    areaCreator = new ItemStack(Material.STICK);
    ItemMeta im = areaCreator.getItemMeta();
    im.setDisplayName(ChatColor.RED+"Area Creator");
    im.setLore(Arrays.asList(ChatColor.RED+"Left Click to set pos1(min)",ChatColor.RED+"Right Click to set pos2(max)"));
    areaCreator.setItemMeta(im);
    }

    //Config areamob
    private void createAreamob() {
        areamob = new File(getDataFolder(),"areamob.yml");
        if(!areamob.exists()) {
            areamob.getParentFile().mkdirs();
            saveResource("areamob.yml",true);
        }
        loadAreamob();
    }
    public void loadAreamob() {
       areamobcfg = YamlConfiguration.loadConfiguration(areamob);
    }
    public void saveAreamob() {
        try {
            areamobcfg.save(areamob);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getAreamobcfg() {
        return areamobcfg;
    }

    //Config lootTable
    private void createloottable() {
        loottable = new File(getDataFolder(),"loottable.yml");
        if(!loottable.exists()) {
            loottable.getParentFile().mkdirs();
            saveResource("loottable.yml",true);
        }
        loadloottable();
    }
    public void loadloottable() {
        loottablecfg = YamlConfiguration.loadConfiguration(loottable);
    }
    public void saveloottable() {
        try {
            loottablecfg.save(loottable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getloottablecfg() {
        return loottablecfg;
    }

}
