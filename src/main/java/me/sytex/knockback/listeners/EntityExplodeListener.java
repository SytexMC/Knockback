package me.sytex.knockback.listeners;

import me.sytex.knockback.handlers.KnockbackHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onEntityExplode(EntityExplodeEvent event) {
    if (event.getEntity().getType() != EntityType.END_CRYSTAL) return;

    KnockbackHandler.handle(event.getLocation());
  }
}
