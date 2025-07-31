/*
 * This file is part of Knockback, licensed under GPL v3.
 *
 * Copyright (c) 2025 Sytex <sytex@duck.com>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

      if (reduction == 0.0) continue;

      double prevBaseValue = attributeInstance.getBaseValue();

      attributeInstance.setBaseValue(reduction);

      Knockback.scheduler.runTaskLater(Knockback.plugin, () -> attributeInstance.setBaseValue(prevBaseValue), 1);
    }
  }
}
