package me.Stellrow.HuntingGrounds.managers;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.areamob.AreaMob;
import me.Stellrow.HuntingGrounds.utils.CUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class AreaMobManager {
    private final HuntingGrounds pl;
    private Set<AreaMob> mobs = new HashSet<AreaMob>();

    public AreaMobManager(HuntingGrounds pl) {
        this.pl = pl;
        buildAreaMobFromConfig();
    }
    //Utility AreaMob
    private void buildAreaMobFromConfig(){
        int areaMobCount = 0;
        if(pl.getAreamobcfg().contains("AreaMobs")){
            for(String areaMob : pl.getAreamobcfg().getConfigurationSection("AreaMobs").getKeys(false)){
                EntityType type = EntityType.valueOf(pl.getAreamobcfg().getString("AreaMobs."+areaMob+".Type"));
                String name = null;
                if(pl.getAreamobcfg().contains("AreaMobs."+areaMob+".EntityName")){
                    name=pl.getAreamobcfg().getString("AreaMobs."+areaMob+".EntityName");
                }
                AreaMob am = new AreaMob(pl,areaMob,type,name);
                mobs.add(am);
                areaMobCount++;
            }
        }
        pl.getServer().getConsoleSender().sendMessage("[HuntingGrounds]Found "+ areaMobCount + " registered custom mobs!");
    }
    public AreaMob getAreaMobByName(String name){
        for(AreaMob am : mobs){
            if(am.getName().equals(name)){
                return am;
            }
        }
        return null;
    }
    public void createMob(String entityType,String mobName,@Nullable String entityName){
        EntityType type = EntityType.valueOf(mobName.toUpperCase());
        AreaMob areaMob = new AreaMob(pl,mobName,type,null);
        mobs.add(areaMob);
        pl.getAreamobcfg().set("AreaMobs."+areaMob.getName()+".Type",areaMob.getEntityType().toString());
        pl.saveAreamob();
    }
    public void deleteMob(String mobName, Player admin){
        if(getAreaMobByName(mobName)!=null){
            mobs.remove(getAreaMobByName(mobName));
            pl.getAreamobcfg().set("AreaMobs."+mobName,null);
            pl.saveAreamob();
            admin.sendMessage(CUtils.asGreen("Deleted the mob!"));
            return;
        }
        admin.sendMessage(CUtils.asRed("No mob found with that name!"));
    }

}
