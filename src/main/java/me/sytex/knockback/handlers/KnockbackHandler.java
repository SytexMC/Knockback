package me.sytex.knockback.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class KnockbackHandler {

  public static void handle(Location location) {
    for (LivingEntity livingEntity : location.getNearbyLivingEntities(12)) {
      AttributeInstance attributeInstance = livingEntity.getAttribute(Attribute.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE);

      if (attributeInstance == null) continue;

      int blastProtection = 0;

      for (ItemStack armor : livingEntity.getEquipment().getArmorContents()) {
        if (armor != null && armor.getType() != Material.AIR) {
          blastProtection += armor.getEnchantmentLevel(Enchantment.BLAST_PROTECTION);
        }
      }

      double reduction = blastProtection > 4 ? -(blastProtection - 4) * 0.15 : 0.0;

      attributeInstance.setBaseValue(reduction);
    }
  }
}
