package me.Stellrow.HuntingGrounds.commands;

import me.Stellrow.HuntingGrounds.area.Area;
import me.Stellrow.HuntingGrounds.areamob.AreaMob;
import me.Stellrow.HuntingGrounds.HuntingGrounds;
import me.Stellrow.HuntingGrounds.LootTable.LootTable;
import me.Stellrow.HuntingGrounds.utils.CUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HuntingGroundsCommand implements CommandExecutor {
    private final HuntingGrounds pl;
    private String prefix = ChatColor.GREEN+"[HuntingGrounds] "+ChatColor.GRAY;

    public HuntingGroundsCommand(HuntingGrounds pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(args.length==1&&args[0].equalsIgnoreCase("reload")){
            pl.reloadConfig();
            p.sendMessage(CUtils.asGreen("Reloaded the configuration!"));
            return true;
        }
        if(args.length>=4){
            if(args[0].equalsIgnoreCase("create")){
                LootTable toSet = null;
                if(args.length==5){
                    toSet = pl.getLootTableManager().getLootTableByName(args[4]);
                }
                pl.getAreaManager().createArea(args[1],args[2],args[3],p,toSet);
                return true;
            }
            if(args[0].equalsIgnoreCase("mob")){
                //Create
                if(args[1].equalsIgnoreCase("create")){
                    pl.getAreaMobManager().createMob(args[3],args[2],null);
                p.sendMessage("Created the mob!");
                return true;
                }
                //Set equipment
                if(args[1].equalsIgnoreCase("gear")){
                    if(args[2].equalsIgnoreCase("set")){
                        ItemStack itemInHand = p.getInventory().getItemInMainHand();
                        if(args[4].equalsIgnoreCase("helmet")){
                            if(itemInHand==null){
                                pl.getAreaMobManager().getAreaMobByName(args[3]).removeHelmet();
                                pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Helmet",null);
                                pl.saveAreamob();
                                return true;
                            }
                            pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Helmet",p.getInventory().getItemInMainHand());
                            pl.getAreaMobManager().getAreaMobByName(args[3]).setHelmet(p.getInventory().getItemInMainHand());
                            pl.saveAreamob();
                        }
                        if(args[4].equalsIgnoreCase("chestplate")){
                            if(itemInHand==null){
                                pl.getAreaMobManager().getAreaMobByName(args[3]).removeChestPlate();
                                pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.ChestPlate",null);
                                pl.saveAreamob();
                                return true;
                            }
                            pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.ChestPlate",p.getInventory().getItemInMainHand());
                            pl.getAreaMobManager().getAreaMobByName(args[3]).setChestplate(p.getInventory().getItemInMainHand());
                            pl.saveAreamob();
                        }
                        if(args[4].equalsIgnoreCase("leggings")){
                            if(itemInHand==null){
                                pl.getAreaMobManager().getAreaMobByName(args[3]).removeLeggings();
                                pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Leggings",null);
                                pl.saveAreamob();
                                return true;
                            }
                            pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Leggings",p.getInventory().getItemInMainHand());
                            pl.getAreaMobManager().getAreaMobByName(args[3]).setLeggings(p.getInventory().getItemInMainHand());
                            pl.saveAreamob();
                        }
                        if(args[4].equalsIgnoreCase("boots")){
                            if(itemInHand==null){
                                pl.getAreaMobManager().getAreaMobByName(args[3]).removeBoots();
                                pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Boots",null);
                                pl.saveAreamob();
                                return true;
                            }
                            pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Boots",p.getInventory().getItemInMainHand());
                            pl.getAreaMobManager().getAreaMobByName(args[3]).setBoots(p.getInventory().getItemInMainHand());
                            pl.saveAreamob();
                        }
                        if(args[4].equalsIgnoreCase("hand")){
                            if(itemInHand==null){
                                pl.getAreaMobManager().getAreaMobByName(args[3]).removeHand();
                                pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Hand",null);
                                pl.saveAreamob();
                                return true;
                            }
                            pl.getAreamobcfg().set("AreaMobs."+args[3]+".Equipment.Hand",p.getInventory().getItemInMainHand());
                            pl.getAreaMobManager().getAreaMobByName(args[3]).setHand(p.getInventory().getItemInMainHand());
                            pl.saveAreamob();
                        }
                        p.sendMessage(CUtils.asGreen("Changed the mob equipment!"));
                        return true;
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("setname")){
                    StringBuilder builder = new StringBuilder();
                    for(int x = 3;x<=args.length-1;x++){
                        builder.append(args[x]);
                    }
                    pl.getAreaMobManager().getAreaMobByName(args[2]).rename(builder.toString());
                    pl.getAreamobcfg().set("AreaMobs."+args[2]+".EntityName",builder.toString());
                    pl.saveAreamob();
                }
            }
        }
        if(args.length==1){
            if(args[0].equalsIgnoreCase("creator")){
                p.getInventory().addItem(pl.areaCreator);
                return true;
            }
            if(args[0].equalsIgnoreCase("loottable")){
                p.sendMessage(prefix+ "Here is a list of all available loottable commands!");
                p.sendMessage(prefix+"/huntinggrounds loottable create <name>");
                p.sendMessage(prefix+"/huntinggrounds loottable edit <name>");
                p.sendMessage(prefix+"/huntinggrounds loottable delete <name>");
                return true;
            }
            return true;
        }
        if(args.length==3){
            if(args[0].equalsIgnoreCase("setlimit")){
                if(pl.getAreaManager().getAreaByName(args[1])!=null){
                    pl.getAreaManager().getAreaByName(args[1]).setMobLimit(CUtils.asInteger(args[2],p));
                        p.sendMessage(CUtils.asGreen("Changed the limit with success!"));
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("mob")&&args[1].equalsIgnoreCase("delete")){
                pl.getAreaMobManager().deleteMob(args[2],p);
                return true;
            }
            if(args[0].equalsIgnoreCase("setloottable")){
                LootTable lt = pl.getLootTableManager().getLootTableByName(args[2]);
                if(lt==null){
                    p.sendMessage(prefix+"No LootTable found with that name!");
                    return true;
                }
                Area area = pl.getAreaManager().getAreaByName(args[1]);
                if(area==null){
                    p.sendMessage(prefix+"No Area found with that name!");
                    return true;
                }
                area.setLootTable(lt);
                p.sendMessage(prefix+"Succsessfully changed the LootTable!");
                return true;
            }
            if(args[0].equalsIgnoreCase("setmob")){
                AreaMob lt = pl.getAreaMobManager().getAreaMobByName(args[2]);
                if(lt==null){
                    p.sendMessage(prefix+"No mob found with that name!");
                    return true;
                }
                Area area = pl.getAreaManager().getAreaByName(args[1]);
                if(area==null){
                    p.sendMessage(prefix+"No Area found with that name!");
                    return true;
                }
                area.setAreaMob(lt);
                p.sendMessage(prefix+"Succsessfully changed the mob!");
                return true;
            }


        }
        if(args.length==3){
            if(args[0].equalsIgnoreCase("loottable")){
                //Create
                if(args[1].equalsIgnoreCase("create")){
                    pl.getLootTableManager().createLootTable(args[2],p);
                    return true;
                }
                //Edit
                if(args[1].equalsIgnoreCase("edit")){
                LootTable lt = pl.getLootTableManager().getLootTableByName(args[2]);
                if(lt==null){
                    p.sendMessage(prefix+"No LootTable found with that name!");
                    return true;
                }
                lt.openInventory(p);
                return true;
                }
                //Delete
                if(args[1].equalsIgnoreCase("delete")){
                    pl.getLootTableManager().deleteLootTable(args[2],p);
                   return true;
                }


                return true;
            }

        }

        if(args.length==2){
            if(args[0].equalsIgnoreCase("delete")){
                pl.getAreaManager().deleteArea(args[1],p);
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                if(args[1].equalsIgnoreCase("creator")){
                    p.sendMessage(prefix+ChatColor.GOLD+"Creator help page!");
                    p.sendMessage(prefix+"Area Creator - Give stick to set location to a region!");
                    p.sendMessage(prefix+"/huntinggrounds creator");
                    p.sendMessage(prefix+"Left click to set the minimum(bottom corner),right click to set the maximum(top corner)");
                    return true;
                }
                if(args[1].equalsIgnoreCase("area")){
                    p.sendMessage(prefix+ChatColor.GOLD+"Area help page!");
                    p.sendMessage(prefix+"/huntinggrounds create <areaName> <mob> <limit>");
                    p.sendMessage(prefix+"/huntinggrounds setLootTable <areaName> <lootTable>");
                    p.sendMessage(prefix+"/huntinggrounds delete <areaName>");
                    return true;
                }
                if(args[1].equalsIgnoreCase("mob")){
                    p.sendMessage(prefix+ChatColor.GOLD+"Mob help page!");
                    p.sendMessage(prefix+"/huntinggrounds mob create <name> <entityType>");
                    p.sendMessage(prefix+"/huntinggrounds mob gear set <mobName> helmet/chestplate/leggings/boots/hand - Hold item in hand");
                    p.sendMessage(prefix+"/huntinggrounds mob setName <mobName> [New name]");
                    p.sendMessage(prefix+"/huntinggrounds mob delete <mobName>");
                    return true;
                }
                if(args[1].equalsIgnoreCase("loottable")){
                    p.sendMessage(prefix+ChatColor.GOLD+"Loottable help page!");
                    p.sendMessage(prefix+"/huntinggrounds loottable create <name>");
                    p.sendMessage(prefix+"/huntinggrounds loottable edit <name>");
                    p.sendMessage(prefix+"/huntinggrounds loottable delete <name>");
                    return true;
                }

            return true;
            }

        }
        p.sendMessage(prefix+"Here is a list of all available commands!");
        p.sendMessage(prefix+"/huntinggrounds help creator - For a quick guide on the creator");
        p.sendMessage(prefix+"/huntinggrounds help Area - For a list of commands available for area");
        p.sendMessage(prefix+"/huntinggrounds help mob - For a list of commands available for mob");
        p.sendMessage(prefix+"/huntinggrounds help loottable - For a list of commands available for loottable");
        return true;
    }

}
