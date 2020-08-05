package me.Stellrow.HuntingGrounds.area;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class Cuboid {
    private World world;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int z1;
    private int z2;
    public Cuboid(Location min,Location max){
        this.world=min.getWorld();
        this.x1 = Math.min(min.getBlockX(),max.getBlockX());
        this.x2 = Math.max(min.getBlockX(),max.getBlockX());
        this.y1 = Math.min(min.getBlockY(),max.getBlockY());
        this.y2 = Math.max(min.getBlockY(),max.getBlockY());
        this.z1 = Math.min(min.getBlockZ(),max.getBlockZ());
        this.z2 = Math.max(min.getBlockZ(),max.getBlockZ());
    }
    public boolean isInArea(Location loc){
        return loc.getBlockX() >= x1 && loc.getBlockX() <= x2
                && loc.getBlockY() >= y1 && loc.getBlockY() <= y2
                && loc.getBlockZ() >= z1 && loc.getBlockZ() <= z2;
    }
    public Cuboid returnCuboid(){
        return this;
    }
    public Location getMin(){
        return new Location(world,x1,y1,z1);
    }
    public Location getMax(){
        return new Location(world,x2,y2,z2);
    }
    public World getWorld(){
        return world;
    }
    public Location getRandomLocationInside(){
        Random rnd = new Random();
        int x = rnd.nextInt((x2-x1)+1)+x1;
        int y = y1+1;
        int z = rnd.nextInt((z2-z1)+1)+z1;
        Location toRet = new Location(world,x,y,z);
        if(toRet.getBlock().getType()!=Material.AIR){
            return getRandomLocationInside();
        }
        if(world.getBlockAt(toRet.add(0,1,0)).getType()!= Material.AIR){
            if(world.getBlockAt(toRet.subtract(0,1,0)).getType()==Material.AIR){
                return getRandomLocationInside();
            }
            return getRandomLocationInside();
        }
        return toRet;
    }
}
