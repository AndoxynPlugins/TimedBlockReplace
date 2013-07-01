/*
 * Author: Dabo Ross
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.timedblockchange;

import org.bukkit.block.Block;

/**
 *
 * @author daboross
 */
public class BlockReplaceTask implements Runnable {

    private final Block blockToReplace;
    private final int toBlockID;
    private final int fromBlockID;

    public BlockReplaceTask(Block blockToReplace, int toBlock) {
        this.blockToReplace = blockToReplace;
        this.toBlockID = toBlock;
        this.fromBlockID = blockToReplace.getTypeId();
    }

    @Override
    public void run() {
        BlockPlaceListener.currentlyHeldBlocks.remove(blockToReplace.getLocation());
        if (blockToReplace.getTypeId() == fromBlockID) {
            blockToReplace.setTypeId(toBlockID);
        }
    }
}
