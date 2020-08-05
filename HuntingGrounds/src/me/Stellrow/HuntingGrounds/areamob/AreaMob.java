package me.Stellrow.HuntingGrounds.areamob;

import me.Stellrow.HuntingGrounds.area.Area;
import me.Stellrow.HuntingGrounds.HuntingGrounds;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nullable;

public class AreaMob {
    private final HuntingGrounds pl;
    private EntityType entityType;
    private Entity toSpawn;
    private String name;
    private ItemStack helmet=null;
    private ItemStack chestplate=null;
    private ItemStack leggings=null;
    private ItemStack boots=null;
    private ItemStack hand=null;
    private String entityDisplayName;
    public AreaMob(HuntingGrounds pl,String name,EntityType entityType,@Nullable String entityDisplayName){
        this.entityDisplayName=entityDisplayName;
        this.name=name;
        this.pl=pl;
        this.entityType=entityType;
        loadEquipment();
    }
    public EntityType getEntityType(){
        return entityType;
    }

    public Entity spawnMob(World world, Location location, Area area){
        toSpawn = world.spawnEntity(location,entityType);
        toSpawn.setMetadata("HuntingGroundsMob",new FixedMetadataValue(pl,area.getName()));
        setEquipment();
        if(entityDisplayName!=null) {
            toSpawn.setCustomName(ChatColor.translateAlternateColorCodes('&', entityDisplayName));
        }
        return toSpawn;
    }
    public void rename(String newName){
        this.entityDisplayName=newName;
    }
    public String getName() {
        return name;
    }
    public void setHelmet(ItemStack helmet){
        this.helmet=helmet;
    }
    private void setEquipment(){
        LivingEntity lv = (LivingEntity) toSpawn;
        lv.getEquipment().setHelmet(helmet);
        lv.getEquipment().setChestplate(chestplate);
        lv.getEquipment().setLeggings(leggings);
        lv.getEquipment().setBoots(boots);
        lv.getEquipment().setItemInMainHand(hand);
        lv.getEquipment().setHelmetDropChance(0);
        lv.getEquipment().setChestplateDropChance(0);
        lv.getEquipment().setLeggingsDropChance(0);
        lv.getEquipment().setBootsDropChance(0);
        lv.getEquipment().setItemInMainHandDropChance(0);
    }
    public void loadEquipment(){
        if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment")){
            //Load gear
            LivingEntity ent = (LivingEntity) toSpawn;
            if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment.Helmet")) {
                helmet = pl.getAreamobcfg().getItemStack("AreaMobs."+name+".Equipment.Helmet");
            }
            if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment.ChestPlate")) {
                chestplate = pl.getAreamobcfg().getItemStack("AreaMobs."+name+".Equipment.ChestPlate");
            }
            if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment.Leggings")) {
                leggings = pl.getAreamobcfg().getItemStack("AreaMobs."+name+".Equipment.Leggings");
            }
            if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment.Boots")) {
                boots = pl.getAreamobcfg().getItemStack("AreaMobs."+name+".Equipment.Boots");
            }
            if(pl.getAreamobcfg().contains("AreaMobs."+name+".Equipment.Hand")) {
                hand = pl.getAreamobcfg().getItemStack("AreaMobs."+name+".Equipment.Hand");
            }

        }
    }

    public void setChestplate(@Nullable ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public void setLeggings(@Nullable ItemStack leggings) {
        this.leggings = leggings;
    }

    public void setBoots(@Nullable ItemStack boots) {
        this.boots = boots;
    }
    public void setHand(@Nullable ItemStack hand){
        this.hand=hand;
    }
    public void removeChestPlate(){
        this.chestplate=null;

    }
    public void removeHelmet(){
        this.helmet=null;
    }
    public void removeLeggings(){
        this.leggings=null;
    }
    public void removeBoots(){
this.boots=null;
    }
    public void removeHand(){
this.hand=null;
    }
}
