package me.sytex.knockback.listeners;

import static me.sytex.knockback.handlers.KnockbackHandler.applyCustomKnockback;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class BlockExplodeListener implements Listener {

  @EventHandler
  public void onBlockExplode(@NotNull BlockExplodeEvent event) {
    if (event.getExplodedBlockState().getType() != Material.RESPAWN_ANCHOR) return;

    applyCustomKnockback(event.getBlock().getLocation());
  }
}
