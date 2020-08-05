package me.Stellrow.HuntingGrounds.LootTable;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.area.Area;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class LootTable implements Listener {
    private final HuntingGrounds pl;
    private HashMap<Integer,ItemStack> itemStackList = new HashMap<Integer, ItemStack>();
    private String name;
    private Inventory lootTableInventory;

    public LootTable(HuntingGrounds pl,String name) {
        this.name=name;
        this.pl = pl;
        lootTableInventory = Bukkit.createInventory(null,54,"LootTable "+name);
        loadInventoryFromConfig();
        pl.getServer().getPluginManager().registerEvents(this,pl);
    }
    private void loadInventoryFromConfig(){
        itemStackList.clear();
        if(!pl.getloottablecfg().contains("LootTables."+name)){
            return;
        }
        for(String s : pl.getloottablecfg().getConfigurationSection("LootTables."+name).getKeys(false)){
        itemStackList.put(Integer.parseInt(s),pl.getloottablecfg().getItemStack("LootTables."+name+"."+s));
        }
    }
    public String getName(){
        return name;
    }
    public void openInventory(Player p){
        lootTableInventory.clear();
        for(Integer i : itemStackList.keySet()){
        lootTableInventory.setItem(i,itemStackList.get(i));
        }
        p.openInventory(lootTableInventory);
    }

    public ItemStack returnItem(){
        Random rnd = new Random();
        if(itemStackList.size()<=1){
            return itemStackList.get(0);
        }
        if(itemStackList.size()==0){
            return null;
        }
        return itemStackList.get(rnd.nextInt(itemStackList.size()-1));
    }
    @EventHandler
    public void closeEvent(InventoryCloseEvent e){
        if(e.getInventory().equals(lootTableInventory)){
            itemStackList.clear();
            pl.getloottablecfg().set("LootTables."+name,null);
            for(int x=0;x<lootTableInventory.getSize();x++){
                if(lootTableInventory.getItem(x)!=null){
                itemStackList.put(x,lootTableInventory.getItem(x));
                pl.getloottablecfg().set("LootTables."+name+"."+x,lootTableInventory.getItem(x));
                }
            }
            pl.saveloottable();
            e.getPlayer().sendMessage(ChatColor.GREEN+"[HuntingGrounds] "+ChatColor.GRAY+"Succsessfully saved the loottable!");
            updateReferences();
        }

    }
    private void updateReferences(){
        for(Area a : pl.getAreaManager().getAreas()){
            if(a.getLootTable().getName().equals(this.getName())){
                a.setLootTable(this);
            }
        }
    }

}
