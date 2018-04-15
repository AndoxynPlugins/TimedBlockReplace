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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author daboross
 */
public class BlockReplaceTask implements Runnable {

    private final BlockPlaceListener bpl;
    final Block blockToReplace;
    final Material fromMaterial;
    final Material toMaterial;
    private BukkitTask task;
    private boolean canceled = false;

    public BlockReplaceTask(BlockPlaceListener bpl, Block blockToReplace, Material endMaterial) {
        this.bpl = bpl;
        this.blockToReplace = blockToReplace;
        this.toMaterial = endMaterial;
        this.fromMaterial = blockToReplace.getType();
    }

    @Override
    public void run() {
        if (!canceled) {
            BlockPlaceListener.locationsCurrentlyWaiting.remove(blockToReplace.getLocation());
            if (blockToReplace.getType() == fromMaterial) {
                blockToReplace.setType(toMaterial);
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
