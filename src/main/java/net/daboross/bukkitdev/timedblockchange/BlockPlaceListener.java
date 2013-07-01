/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockchange;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author daboross
 */
public class BlockPlaceListener implements Listener {

    private final TimedBlockChange tbc;
    private final Set<Integer> blocksToReplace = new HashSet<Integer>();
    static final Set<Location> currentlyHeldBlocks = new HashSet<Location>();

    public BlockPlaceListener(TimedBlockChange tbc) {
        this.tbc = tbc;
        blocksToReplace.addAll(tbc.getConfig().getIntegerList("from-blocks"));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent bpe) {
        if (bpe.isCancelled()) {
            return;
        }
        if (blocksToReplace.contains(bpe.getBlockPlaced().getTypeId()) && !currentlyHeldBlocks.contains(bpe.getBlockPlaced().getLocation())) {
            Block b = bpe.getBlockPlaced();
            runTask(b, tbc.getConfig().getInt("to-blocks." + b.getTypeId(), -1), tbc.getConfig().getInt("block-times." + b.getTypeId(), -1));
        }
    }

    private void runTask(Block b, int toBlock, int timeTillChange) {
        if (toBlock == -1) {
            tbc.getLogger().log(Level.WARNING, "The block {0} is in from-blocks, but not to-blocks!", b.getTypeId());
        } else if (timeTillChange == -1) {
            tbc.getLogger().log(Level.WARNING, "The block {0} is in from-blocks, but not block-times!", b.getTypeId());
        }
        currentlyHeldBlocks.add(b.getLocation());
        BlockReplaceTask task = new BlockReplaceTask(b, toBlock);
        Bukkit.getScheduler().runTaskLater(tbc, task, timeTillChange * 20L);
    }
}
