/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.timedblockreplace;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author daboross
 */
public class BlockPlaceListener implements Listener {

    private final TimedBlockReplacePlugin tbc;
    static final Map<Location, BlockReplaceTask> locationsCurrentlyWaiting = new HashMap<Location, BlockReplaceTask>();

    public BlockPlaceListener(TimedBlockReplacePlugin tbc) {
        this.tbc = tbc;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent bpe) {
        if ((!bpe.isCancelled())) {
            testBlock(bpe.getBlockPlaced());
        }
    }

    void testBlock(Block b) {
        if (tbc.configuration.shouldReplace(b.getType())) {
            BlockReplaceTask brt = locationsCurrentlyWaiting.get(b.getLocation());
            if (brt != null) {
                brt.cancel();
            }
            Configuration.ReplaceInfo info = tbc.configuration.replacing(b.getType());
            runTask(b, info.material, info.timeout);
        }
    }

    private void runTask(Block b, Material toBlock, int timeTillChange) {
        BlockReplaceTask task = new BlockReplaceTask(this, b, toBlock);
        locationsCurrentlyWaiting.put(b.getLocation(), task);
        BukkitTask bt = Bukkit.getScheduler().runTaskLater(tbc, task, timeTillChange * 20L);
        task.setTask(bt);
    }
}
