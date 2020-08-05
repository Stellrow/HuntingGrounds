package me.Stellrow.HuntingGrounds.managers;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.LootTable.LootTable;
import me.Stellrow.HuntingGrounds.utils.CUtils;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class LootTableManager {
    private final HuntingGrounds pl;
    private Set<LootTable> lootTables = new HashSet<LootTable>();

    public LootTableManager(HuntingGrounds pl) {
        this.pl = pl;
        buildLootTables();
    }

    public void createLootTable(String lootTableName, Player whoCreates){
        if(getLootTable(lootTableName)!=null){
            whoCreates.sendMessage(CUtils.asRed("A loottable with this name already exists!"));
            return;
        }
        whoCreates.sendMessage(CUtils.asGreen("Succsessfully created the loottable with the name "+ lootTableName));
        LootTable lt = new LootTable(pl,lootTableName);
        pl.getloottablecfg().set("LootTables."+lootTableName,"");
        pl.saveloottable();
        lootTables.add(lt);
    }
    public void deleteLootTable(String lootTableName,Player admin){
        LootTable lt = getLootTableByName(lootTableName);
        if(lt==null){
            admin.sendMessage(CUtils.asRed("No LootTable found with that name!"));
            return;
        }
        pl.getloottablecfg().set("LootTables."+lt.getName(),null);
        pl.saveloottable();
        deleteLootTable(lt);
        admin.sendMessage(CUtils.asGreen("Successfully deleted that LootTable,Mind that deleting an LootTable in use can cause errors!"));
    }
    public LootTable getLootTable(String lootTableName){
        for(LootTable lt : lootTables){
            if(lt.getName().equals(lootTableName)){
                return lt;
            }
        }
        return null;
    }
    //Utility LootTable
    public LootTable getLootTableByName(String name){
        for(LootTable lt : lootTables){
            if(lt.getName().equals(name)){
                return lt;
            }
        }
        return null;
    }
    private void buildLootTables(){
        if(pl.getloottablecfg().contains("LootTables")){
            for(String key : pl.getloottablecfg().getConfigurationSection("LootTables").getKeys(false)){
                lootTables.add(new LootTable(pl,key));
            }
        }
    }
    public void deleteLootTable(LootTable toDelete){
        if(lootTables.contains(toDelete)) {
            lootTables.remove(toDelete);
        }
    }
}
