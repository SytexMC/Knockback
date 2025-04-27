package me.sytex.knockback.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class KnockbackHandler {

  public static void applyCustomKnockback(@NotNull Location location) {
    final Vector explosionPos = location.toVector().add(new Vector(0, -0.5, 0));
    final Collection<Entity> nearbyEntities = location.getNearbyEntities(8.0, 8.0, 8.0);

    for (Entity nearby : nearbyEntities) {
      if (!(nearby instanceof LivingEntity livingEntity)) continue;

      var equipment = livingEntity.getEquipment();
      if (equipment == null) continue;

      int blastProtectionLevel = 0;
      for (ItemStack armor : equipment.getArmorContents()) {
        if (armor == null || armor.getType() == Material.AIR) continue;
        blastProtectionLevel += armor.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
        if (blastProtectionLevel >= 4) break; // No need to check further
      }

      double knockbackReduction = Math.min(0.60, blastProtectionLevel * 0.15);

      Location entityLocation = livingEntity.getLocation();
      Vector toEntity = entityLocation.toVector().subtract(explosionPos);
      double distance = toEntity.length();
      if (distance == 0) continue;

      toEntity.normalize();

      double strength = Math.max(0, 1.5 - (distance / 8.0));

      double yDiff = explosionPos.getY() - entityLocation.getY();
      double verticalFactorFar = (yDiff > 0)
          ? Math.max(0.0, 1.0 - (yDiff / 1.5))
          : Math.min(1.5, 1.0 + (Math.abs(yDiff) / 2.0));
      double verticalFactorClose = 1.0 + (1.0 - distance);

      double weight = Math.min(1.0, distance);
      double verticalFactor = (1.0 - weight) * verticalFactorClose + weight * verticalFactorFar;

      if (distance < 1.5) verticalFactor += (1.5 - distance) * 0.4;

      Vector knockback = toEntity.multiply(strength * (1.0 - knockbackReduction) * verticalFactor);
      livingEntity.setVelocity(livingEntity.getVelocity().add(knockback));
    }
  }
}
