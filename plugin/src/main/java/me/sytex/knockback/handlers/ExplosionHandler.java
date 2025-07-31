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
      double roundedValue = Math.round(value * 100000d) / 100000d;

      double reduction = roundedValue > 0.6 ? 0.6 - roundedValue : 0.0;

      double prevBaseValue = attributeInstance.getBaseValue();

      attributeInstance.setBaseValue(reduction);

      Knockback.scheduler.runTaskLater(Knockback.plugin, () -> attributeInstance.setBaseValue(prevBaseValue), 1);
    }
  }
}
