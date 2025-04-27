package me.sytex.knockback;

import static org.bukkit.Bukkit.getPluginManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderCrystal;
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
    if (!(event.getEntity() instanceof EnderCrystal enderCrystal)) return;

    final Vector explosionPos = enderCrystal.getLocation().toVector().add(new Vector(0, -0.5, 0));

    for (Entity nearby : enderCrystal.getNearbyEntities(10, 10, 10)) {
      if (!(nearby instanceof LivingEntity livingEntity)) continue;

      var equipment = livingEntity.getEquipment();
      if (equipment == null) continue;

      int blastProtectionLevel = 0;
      for (ItemStack armorPiece : equipment.getArmorContents()) {
        if (armorPiece != null && armorPiece.getType() != Material.AIR) {
          blastProtectionLevel += armorPiece.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
        }
      }

      double knockbackReduction = Math.min(0.60, blastProtectionLevel * 0.15);

      Vector toEntity = livingEntity.getLocation().toVector().subtract(explosionPos);
      double distance = toEntity.length();

      toEntity.normalize();

      double strength = Math.max(0, 1.0 - (distance / 10.0));

      double yDiff = explosionPos.getY() - livingEntity.getLocation().getY();
      double verticalFactor = (yDiff > 0) ? Math.max(0.0, 1.0 - (yDiff / 1.5)) : 1.0;

      Vector knockback = toEntity.multiply(strength * (1.0 - knockbackReduction) * verticalFactor);
      livingEntity.setVelocity(knockback);
    }
  }
}
