package us.ajg0702.leaderboards.loaders;

import org.spongepowered.configurate.CommentedConfigurationNode;
import us.ajg0702.leaderboards.LeaderboardPlugin;
import us.ajg0702.utils.common.Messages;

import java.util.Arrays;

public class MessageLoader {
    public static Messages loadMessages(LeaderboardPlugin plugin) {
        return new Messages(plugin.getDataFolder(), plugin.getLogger(), Messages.makeDefaults(
                "signs.top.default", Arrays.asList(
                            "&7&m       &r #{POSITION} &7&m       ",
                            "&6{NAME}",
                            "&e{VALUE} {VALUENAME}",
                            "&7&m                   "
                ),

                "gui.top.default", Arrays.asList(
                        "&7",
                        "    &e{VALUE} {VALUENAME}",
                        "&7"
                ),

                "gui.top.name", "Tops",
                "gui.top.item-name", "&6{NAME} &7- &eTop #{POSITION}",

                "formatted.k", "k",
                "formatted.m", "m",
                "formatted.t", "t",
                "formatted.b", "b",
                "formatted.q", "q",

                "time.w", "w ",
                "time.d", "d ",
                "time.h", "h ",
                "time.m", "m ",
                "time.s", "s",

                "noperm", "You don't have permission to do this!",

                "commands.reload.success", "&aConfigs reloaded!",
                "commands.reload.fail", "&cAn error occurred while reloading one of your configs. Check the console for more info.",

                "commands.export.fileexists", "&cThe file &f{FILE}&c already exists!",
                "commands.export.starting", "&7Fetching all players from the datbase. This might take a bit.",
                "commands.export.fail", "&cAn error occurred while exporting. Check the console for more info.",
                "commands.export.progress", "&eProgress: &f{DONE}&7/&f{TOTAL}&7 boards fetched",
                "commands.export.success", "&aThe cache has been exported to the file &f{FILE}&a!",

                "commands.import.nofile", "&cThe file &f{FILE}&c doesnt exist!",
                "commands.import.starting", "&7Loading all cached stats from &f{FILE}",
                "commands.import.fail", "&cAn error occurred while importing. Check the console for more info.",
                "commands.import.insertprogress", "&eProgress: &f{DONE}&7/&f{TOTAL}&7 boards imported",
                "commands.import.success", "&aThe cache has been imported from the file &f{FILE}&a!",

                "no-data.lb.name", plugin.getAConfig().hasEntry("no-data-name") ?
                        plugin.getAConfig().getString("no-data-name") :
                        "---",
                "no-data.lb.value", plugin.getAConfig().hasEntry("no-data-score") ?
                        plugin.getAConfig().getString("no-data-score") :
                        "---",
                "no-data.extra", plugin.getAConfig().hasEntry("no-data-name") ?
                        plugin.getAConfig().getString("no-data-name") :
                        "---",
                "no-data.rel.position", "{POSITION}",
                "no-data.rel.name", "---",
                "no-data.rel.value", "---"


                ));
    }
}
