/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockreplace;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author daboross
 */
public class BlockReplaceTask implements Runnable {

    private final BlockPlaceListener bpl;
    final Block blockToReplace;
    final int toBlockID;
    final int fromBlockID;
    private BukkitTask task;
    private boolean canceled = false;

    public BlockReplaceTask(BlockPlaceListener bpl, Block blockToReplace, int toBlock) {
        this.bpl = bpl;
        this.blockToReplace = blockToReplace;
        this.toBlockID = toBlock;
        this.fromBlockID = blockToReplace.getTypeId();
    }

    @Override
    public void run() {
        if (!canceled) {
            BlockPlaceListener.locationsCurrentlyWaiting.remove(blockToReplace.getLocation());
            if (blockToReplace.getTypeId() == fromBlockID) {
                blockToReplace.setTypeId(toBlockID);
                bpl.testBlock(blockToReplace);
            }
        }
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void cancel() {
        if (!canceled) {
            BlockPlaceListener.locationsCurrentlyWaiting.remove(blockToReplace.getLocation());
            this.task.cancel();
            canceled = true;
        }
    }
}
