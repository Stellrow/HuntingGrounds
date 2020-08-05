package me.Stellrow.HuntingGrounds.area;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.LootTable.LootTable;
import me.Stellrow.HuntingGrounds.areamob.AreaMob;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class Area {
    private final HuntingGrounds pl;
    private String name;
    private AreaMob areaMob;
    private Cuboid cuboid;
    private Set<Entity> mobsAlive = new HashSet<Entity>();
    private LootTable lootTable;
    private int mobLimit = 0;
    private boolean toCancel = false;

    public Area(HuntingGrounds pl, String name, AreaMob areaMob, Cuboid cuboid, Integer mobLimit, @Nullable LootTable lootTable) {
        if(lootTable!=null){
            this.lootTable=lootTable;
        }
        this.pl = pl;
        this.name=name;
        this.areaMob=areaMob;
        this.cuboid=cuboid;
        this.mobLimit=mobLimit;
        startSpawning();
    }

    public Area getArea(){
        return this;
    }
    public Cuboid getCuboid() {
        return cuboid;
    }

    public AreaMob getAreaMob() {
        return areaMob;
    }

    public String getName() {
        return name;
    }
    public Integer getMobLimit(){
        return mobLimit;
    }
    public void setAreaMob(AreaMob toSet){
        this.areaMob=toSet;
        pl.getConfig().set("Areas."+name+".MobType",toSet.getName());
        pl.saveConfig();
    }
    public void setLootTable(LootTable toSet){
        this.lootTable=toSet;
        pl.getConfig().set("Areas."+name+".LootTable",toSet.getName());
        pl.saveConfig();
    }
    public LootTable getLootTable(){
        return lootTable;
    }
    private void startSpawning(){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(toCancel){
                    this.cancel();
                }
            if(mobsAlive.size()<mobLimit){
                mobsAlive.add(areaMob.spawnMob(cuboid.getWorld(),cuboid.getRandomLocationInside(),getArea()));
            }
            }
        }.runTaskTimer(pl,0,10*20);
    }
    public void removeMob(Entity toRemove){
        mobsAlive.remove(toRemove);
    }
    public void shutDown(){
        toCancel = true;
        for (Entity e : mobsAlive){
            e.remove();
        }
        mobsAlive.clear();
    }
    public void setMobLimit(Integer limit){
        mobLimit=limit;
        pl.getConfig().set("Areas."+getName()+".MobLimit",getMobLimit());
        pl.saveConfig();
    }
}
