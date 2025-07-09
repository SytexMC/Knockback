package me.sytex.knockback.handlers;

import java.util.Collection;
import me.sytex.knockback.Knockback;
import org.bukkit.Location;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ExplosionHandler {

  public static void handle(@NotNull Location location) {
    for (LivingEntity livingEntity : location.getNearbyLivingEntities(12)) {
      AttributeInstance attributeInstance = Knockback.versionHandler.getAttributeInstance(livingEntity);

      if (attributeInstance == null) continue;

      Collection<AttributeModifier> modifiers = attributeInstance.getModifiers();

      if (modifiers.isEmpty()) continue;

      double value = modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
      double reduction = value > 0.6 ? 0.6 - value : 0.0;

      attributeInstance.setBaseValue(reduction);
    }
  }
}
