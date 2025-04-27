package me.sytex.knockback.listeners;

import static me.sytex.knockback.handlers.KnockbackHandler.applyCustomKnockback;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class EntityExplodeListener implements Listener {

  @EventHandler
  public void onEntityExplode(@NotNull EntityExplodeEvent event) {
    if (event.getEntity().getType() != EntityType.END_CRYSTAL) return;

    applyCustomKnockback(event.getLocation());
  }
}
