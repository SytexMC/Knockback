package me.sytex.knockback.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class KnockbackHandler {

  public static void handle(Location location) {
    for (LivingEntity livingEntity : location.getNearbyLivingEntities(12)) {
      AttributeInstance attributeInstance = livingEntity.getAttribute(Attribute.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE);
      if (attributeInstance != null) {
        attributeInstance.setBaseValue(-10.00);

        EntityEquipment equipment = livingEntity.getEquipment();

        int blastProtection = 0;

        if (equipment != null) {
          for (ItemStack armor : equipment.getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
              blastProtection += armor.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
              if (blastProtection >= 4) break;
            }
          }
        }

        double reduction = Math.min(blastProtection * 0.15, 0.60);

        Vector velocity = livingEntity.getVelocity().multiply(1.0 - reduction);
        livingEntity.setVelocity(velocity);
      }
    }
  }
}
