package me.sytex.knockback.handlers;

import me.sytex.knockback.Knockback;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class KnockbackHandler {

  private static final Vector EXPLOSION_OFFSET = new Vector(0, -0.5, 0);

  public static void applyCustomKnockback(@NotNull Location location) {
    final Vector explosionLocation = location.toVector().add(EXPLOSION_OFFSET);
    final World world = location.getWorld();

    Collection<LivingEntity> entities = world.getNearbyLivingEntities(location, 8.0,
        entity -> entity != null && !entity.isDead());

    if (entities.isEmpty()) return;

    CompletableFuture.runAsync(() -> {
      Map<LivingEntity, Vector> knockbackMap = new ConcurrentHashMap<>();

      for (LivingEntity entity : entities) {
        Location entityLocation = entity.getLocation();
        Vector entityVelocity = entity.getVelocity();
        EntityEquipment equipment = entity.getEquipment();

        if (equipment == null) continue;

        int blastProtectionLevel = 0;
        for (ItemStack armor : equipment.getArmorContents()) {
          if (armor == null || armor.getType() == Material.AIR) continue;
          blastProtectionLevel += armor.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
          if (blastProtectionLevel >= 4) break;
        }

        double knockbackReduction = Math.min(0.60, blastProtectionLevel * 0.15);

        Vector toEntity = entityLocation.toVector().subtract(explosionLocation);
        double distance = toEntity.length();

        if (distance > 0) {
          toEntity.multiply(1.0 / distance);
        }

        double strength = Math.max(0, 1.0 - (distance / 8.0));

        double yDiff = explosionLocation.getY() - entityLocation.getY();
        double verticalFactorFar = (yDiff > 0)
            ? Math.max(0.0, 0.7 - (yDiff / 2.0))
            : Math.min(1.2, 1.0 + (Math.abs(yDiff) / 2.0));

        double verticalFactorClose = Math.min(1.2, 1.0 + (1.0 - distance));

        double weight = Math.min(1.0, distance);
        double verticalFactor = (1.0 - weight) * verticalFactorClose + weight * verticalFactorFar;

        if (distance < 1.0) {
          verticalFactor += (1.0 - distance) * 0.3;
        }

        Vector knockback = toEntity.multiply(strength * (1.0 - knockbackReduction) * verticalFactor);
        Vector finalVelocity = entityVelocity.clone().add(knockback);

        knockbackMap.put(entity, finalVelocity);
      }

      Bukkit.getScheduler().runTask(Knockback.getPlugin(Knockback.class), () -> {
        for (Map.Entry<LivingEntity, Vector> entry : knockbackMap.entrySet()) {
          LivingEntity entity = entry.getKey();
          Vector velocity = entry.getValue();
          if (entity.isValid() && !entity.isDead()) {
            entity.setVelocity(velocity);
          }
        }
      });
    });
  }
}
