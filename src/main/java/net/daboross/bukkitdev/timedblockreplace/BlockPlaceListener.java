/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author daboross
 */
public class BlockPlaceListener implements Listener {

    private final TimedBlockReplace tbc;
    private final Set<Integer> blocksToReplace = new HashSet<Integer>();
    static final Map<Location, BlockReplaceTask> locationsCurrentlyWaiting = new HashMap<Location, BlockReplaceTask>();

    public BlockPlaceListener(TimedBlockReplace tbc) {
        this.tbc = tbc;
        blocksToReplace.addAll(tbc.getConfig().getIntegerList(TimedBlockReplace.CONFIG_FROMBLOCK_LIST));
    }

    public void reloadConfig() {
        blocksToReplace.clear();
        blocksToReplace.addAll(tbc.getConfig().getIntegerList(TimedBlockReplace.CONFIG_FROMBLOCK_LIST));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent bpe) {
        if ((!bpe.isCancelled()) && blocksToReplace.contains(bpe.getBlockPlaced().getTypeId())) {
            Block b = bpe.getBlockPlaced();
            BlockReplaceTask brt = locationsCurrentlyWaiting.get(b.getLocation());
            if (brt != null) {
                brt.cancel();
            }
            runTask(b, tbc.getConfig().getInt(TimedBlockReplace.CONFIG_TO_BLOCK_PREFIX + b.getTypeId(), -1), tbc.getConfig().getInt(TimedBlockReplace.CONFIG_TIMES_PREFIX + b.getTypeId(), -1));
        }
    }

    private void runTask(Block b, int toBlock, int timeTillChange) {
        if (toBlock < 0) {
            tbc.getLogger().log(Level.WARNING, "The block {0} is in from-blocks, but not to-blocks!", b.getTypeId());
        } else if (timeTillChange < 0) {
            tbc.getLogger().log(Level.WARNING, "The block {0} is in from-blocks, but not block-times!", b.getTypeId());
        } else {
            BlockReplaceTask task = new BlockReplaceTask(b, toBlock);
            locationsCurrentlyWaiting.put(b.getLocation(), task);
            BukkitTask bt = Bukkit.getScheduler().runTaskLater(tbc, task, timeTillChange * 20L);
            task.setTask(bt);
        }
    }
}
