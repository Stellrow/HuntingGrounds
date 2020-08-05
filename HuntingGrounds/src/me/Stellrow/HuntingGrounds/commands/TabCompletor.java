package me.Stellrow.HuntingGrounds.commands;

import me.Stellrow.HuntingGrounds.HuntingGrounds;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import java.util.*;

public class TabCompletor implements TabCompleter {
    private final HuntingGrounds pl;
    //Base command
    private static final List<String> def = Arrays.asList("help", "create","delete", "mob", "loottable", "creator","setlimit","reload");
    private static final List<String> temp = new ArrayList<>();
    private static final List<String> help = Arrays.asList("creator", "area", "mob", "loottable");
    private static final List<String> mob = Arrays.asList("create", "gear", "setname", "delete");


    private static final List<String> mobCreate = Arrays.asList("name");
    private static final List<String> mobType = Arrays.asList("EntityType");
    private static final List<String> mobDelete = Arrays.asList("mobName");

    private static final List<String> mobGearSet = Arrays.asList("set");
    private static final List<String> mobGearPieces = Arrays.asList("helmet","chestplate","leggings","boots","hand");

    private static final List<String> mobToRename = Arrays.asList("mobToRename");
    private static final List<String> mobNewName = Arrays.asList("newName");


    private static final List<String> loottable = Arrays.asList("create", "edit", "delete");


    private static final List<String> areaCreate = Arrays.asList("create","delete");
    private static final List<String> areaName = Arrays.asList("areaName");
    private static final List<String> areaMob = Arrays.asList("mob(Created HuntingGrounds Mob)");
    private static final List<String> areaLimit = Arrays.asList("mobLimit");
    private static final List<String> areaLootTable = Arrays.asList("lootTable(Can be empty)");

    public TabCompletor(HuntingGrounds pl) {
        this.pl = pl;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return temp;
        }
        if (args.length == 1) {
                return def;
        }
        if(args.length>=2) {
            if (args[0].equalsIgnoreCase("help")) {
                return help;
            }
                if (args[0].equalsIgnoreCase("create")) {
                    switch (args.length) {
                        case 2:
                            return areaName;
                        case 3:
                            return areaMob;
                        case 4:
                            return areaLimit;
                        case 5:
                            return areaLootTable;
                        default:
                            return Collections.emptyList();
                    }
            }
            if (args[0].equalsIgnoreCase("delete")) {
                switch (args.length) {
                    case 2:
                        return areaName;
                    default:
                        return Collections.emptyList();
                }
            }
            if (args[0].equalsIgnoreCase("setlimit")) {
                switch (args.length) {
                    case 2:
                        return areaName;
                    case 3:
                        return areaLimit;
                    default:
                        return Collections.emptyList();
                }
            }
            if (args[0].equalsIgnoreCase("mob")) {
                switch(args[1].toLowerCase()){
                    case "create":{
                        switch(args.length){
                            case 3:
                                return mobCreate;
                            case 4:
                                for(EntityType type : EntityType.values()){
                                    temp.add(type.name());
                                }
                                return temp;
                            default:
                                return Collections.emptyList();
                        }
                    }
                    case "delete":{
                        switch (args.length){
                            case 3:
                                return mobDelete;
                            default:
                                return Collections.emptyList();
                        }
                    }
                    case "gear":{
                        switch (args.length){
                            case 3:
                                return mobGearSet;
                            case 4:
                                return mobDelete;
                            case 5:
                                return mobGearPieces;

                            default:
                                return Collections.emptyList();
                        }
                    }
                    case "setname":{
                        switch (args.length){
                            case 3:
                                return mobToRename;
                            case 4:
                                return mobNewName;
                            default:
                                return Collections.emptyList();

                        }

                    }
                }
                return mob;
                }
            if (args[0].equalsIgnoreCase("loottable")) {
                switch (args[1].toLowerCase()){
                    case "create":{
                        switch(args.length){
                            case 3:
                                return mobCreate;
                            default:
                                return Collections.emptyList();
                        }
                    }
                    case "edit":{
                        switch(args.length){
                            case 3:
                                return mobCreate;
                            default:
                                return Collections.emptyList();
                        }
                    }
                    case "delete":{
                        switch(args.length){
                            case 3:
                                return mobCreate;
                            default:
                                return Collections.emptyList();
                        }
                    }
                }
                return loottable;
            }



        }
        return Collections.emptyList();
    }
}
