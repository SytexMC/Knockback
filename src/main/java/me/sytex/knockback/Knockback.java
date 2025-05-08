package me.sytex.knockback;

import static org.bukkit.Bukkit.getPluginManager;

import me.sytex.knockback.listeners.BlockExplodeListener;
import me.sytex.knockback.listeners.EntityExplodeListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Knockback extends JavaPlugin {

  @Override
  public void onEnable() {
    getPluginManager().registerEvents(new BlockExplodeListener(), this);
    getPluginManager().registerEvents(new EntityExplodeListener(), this);
  }
}
