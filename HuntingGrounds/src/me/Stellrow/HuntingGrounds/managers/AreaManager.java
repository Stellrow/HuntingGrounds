package me.Stellrow.HuntingGrounds.managers;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.LootTable.LootTable;
import me.Stellrow.HuntingGrounds.area.Area;
import me.Stellrow.HuntingGrounds.area.Cuboid;
import me.Stellrow.HuntingGrounds.utils.CUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class AreaManager {
    private final HuntingGrounds pl;
    private Set<Area> areas = new HashSet<Area>();

    public AreaManager(HuntingGrounds pl) {
        this.pl = pl;
        buildAreasFromConfig();
    }
    public Area getAreaByMetaData(String s){
        for(Area a : areas){
            if(a.getName().equals(s)){
                return a;
            }
        }
        return null;
    }
    public Area getAreaByName(String name){
        for(Area  a : areas){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }
    private void buildAreasFromConfig(){
        int arenas = 0;
        if(pl.getConfig().contains("Areas")){
            for(String area : pl.getConfig().getConfigurationSection("Areas").getKeys(false)){
                LootTable lt = null;
                if(pl.getConfig().contains("Areas."+area+".LootTable")){
                    lt = new LootTable(pl,pl.getConfig().getString("Areas."+area+".LootTable"));
                }
                Area areaT = new Area(pl,area,pl.getAreaMobManager().getAreaMobByName(pl.getConfig().getString("Areas."+area+".MobType")),new Cuboid(buildLocation(pl.getConfig().getString("Areas."+area+".Cuboid.Min")),buildLocation(pl.getConfig().getString("Areas."+area+".Cuboid.Max"))),pl.getConfig().getInt("Areas."+area+".MobLimit"),lt);
                areas.add(areaT);
                arenas++;
            }
        }
        pl.getServer().getConsoleSender().sendMessage("[HuntingGrounds]Found a total of " +arenas+ " areas!");
    }
    private Location buildLocation(String s){
        String[] toTranslate = s.split(" ");
        Location toRet = new Location(Bukkit.getWorld(toTranslate[0]),Double.parseDouble(toTranslate[1]),Double.parseDouble(toTranslate[2]),Double.parseDouble(toTranslate[3]));
        pl.getServer().getConsoleSender().sendMessage(toRet.toString());
        return toRet;
    }
    public void shutDownAreas(){
        for(Area a : areas){
            a.shutDown();
        }
    }
    public void deleteArea(String areaName, Player admin){
        if(getAreaByName(areaName)!=null){
            getAreaByName(areaName).shutDown();
            areas.remove(getAreaByName(areaName));
            pl.getConfig().set("Areas."+areaName,null);
            pl.saveConfig();
            admin.sendMessage(CUtils.asGreen("Deleted the area!"));
            return;
        }
        admin.sendMessage(CUtils.asRed("No area exists with this name"));
    }

    public void createArea(String areaName, String areaMobName, String mobLimit, Player whoCreated, @Nullable LootTable lootTable){
        if(getAreaByName(areaName)!=null){
        whoCreated.sendMessage(CUtils.asRed("A area with that name already exists!"));
        return;
        }
        if(pl.pos1==null||pl.pos2==null){
            whoCreated.sendMessage(CUtils.asRed("Select the area positions first! /hg creator"));
            return;
        }
        if(!pl.pos1.getWorld().equals(pl.pos2.getWorld())){
            whoCreated.sendMessage(CUtils.asRed("The 2 area positions are in different worlds!"));
            return;
        }
        Area area = new Area(pl,areaName,pl.getAreaMobManager().getAreaMobByName(areaMobName),new Cuboid(pl.pos1,pl.pos2),Integer.parseInt(mobLimit),null);

        pl.getConfig().set("Areas."+area.getName()+".Name",area.getName());
        pl.getConfig().set("Areas."+area.getName()+".MobType",area.getAreaMob().getName());
        Location min = area.getCuboid().getMin();
        String minS = min.getWorld().getName()+" "+min.getBlockX() + " "+min.getBlockY()+" "+min.getBlockZ();
        Location max = area.getCuboid().getMax();
        String maxS = max.getWorld().getName()+" "+max.getBlockX() + " "+max.getBlockY()+" "+max.getBlockZ();
        pl.getConfig().set("Areas."+area.getName()+".Cuboid.Min",minS);
        pl.getConfig().set("Areas."+area.getName()+".Cuboid.Max",maxS);
        pl.getConfig().set("Areas."+area.getName()+".MobLimit",area.getMobLimit());
        pl.saveConfig();
        area.setLootTable(lootTable);
        areas.add(area);

        whoCreated.sendMessage(CUtils.asGreen("Area created with the name "+areaName));
    }
    public Set<Area> getAreas(){
        return areas;
    }
}
