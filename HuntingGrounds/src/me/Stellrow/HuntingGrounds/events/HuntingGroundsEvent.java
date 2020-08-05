package me.Stellrow.HuntingGrounds.events;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.area.Area;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;

public class HuntingGroundsEvent implements Listener {
    private final HuntingGrounds pl;

    public HuntingGroundsEvent(HuntingGrounds pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onCreatureDeath(EntityDeathEvent e){
        if(e.getEntity().hasMetadata("HuntingGroundsMob")){
        String areaName = null;
        Collection<MetadataValue> values = e.getEntity().getMetadata("HuntingGroundsMob");
        for(MetadataValue ob : values){
            if(ob.getOwningPlugin() == pl){
                areaName = ob.asString();
            }
        }
        if(areaName==null){
            return;
        }
        Area ar = pl.getAreaManager().getAreaByMetaData(areaName);
        ar.removeMob(e.getEntity());
        if(!pl.getConfig().getBoolean("General.mobsDropExperience")) {
            e.setDroppedExp(0);
        }
        e.getDrops().clear();
        //Pick item from LootTable
            if(ar.getLootTable()!=null){
                dropItem(e.getEntity().getLocation(),ar.getLootTable().returnItem());
            }
        }
    }
    //Area creator
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem()==null){
            return;
        }
        if(!e.getItem().isSimilar(pl.areaCreator)){
            return;
        }
        if(e.getAction()== Action.LEFT_CLICK_BLOCK){
            e.setCancelled(true);
            Block b = e.getClickedBlock();
            pl.pos1 = b.getLocation();
            e.getPlayer().sendMessage("Selected pos1");
            return;
        }
        if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
            e.setCancelled(true);
            Block b = e.getClickedBlock();
            pl.pos2 = b.getLocation();
            e.getPlayer().sendMessage("Selected pos2");
            return;
        }
    }
    private void dropItem(Location toDrop, ItemStack item){
        toDrop.getWorld().dropItemNaturally(toDrop,item);
    }

}
