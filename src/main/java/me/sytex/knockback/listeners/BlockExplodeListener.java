package me.sytex.knockback.listeners;

import me.sytex.knockback.handlers.KnockbackHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BlockExplodeListener implements Listener {

  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    if (event.getExplodedBlockState().getType() != Material.RESPAWN_ANCHOR) return;

    KnockbackHandler.handle(event.getBlock().getLocation());
  }
}
