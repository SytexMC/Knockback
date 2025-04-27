package me.sytex.knockback;

import static org.bukkit.Bukkit.getPluginManager;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Knockback extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    Location explosionLoc = event.getEntity().getLocation().add(0, -0.5, 0);

    for (Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) {
      if (!(entity instanceof LivingEntity livingEntity)) {
        continue;
      }

      int blastProtectionLevel = 0;
      if (livingEntity.getEquipment() != null) {
        for (ItemStack armor : livingEntity.getEquipment().getArmorContents()) {
          if (armor != null) {
            blastProtectionLevel += armor.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
          }
        }
      }

      double knockbackReduction = Math.min(0.60, blastProtectionLevel * 0.15);

      Vector direction = livingEntity.getLocation().toVector().subtract(explosionLoc.toVector());
      double distance = direction.length();
      if (distance == 0) continue;

      direction.normalize();

      double strength = 1.0 - (distance / 10.0);
      strength = Math.max(0, strength);

      double yDiff = explosionLoc.getY() - livingEntity.getLocation().getY();
      double verticalFactor = 1.0;

      if (yDiff > 0) {
        verticalFactor = Math.max(0.0, 1.0 - (yDiff / 1.5));
      }

      Vector knockback = direction.multiply(strength * (1.0 - knockbackReduction) * verticalFactor);
      livingEntity.setVelocity(knockback);
    }
  }
}
