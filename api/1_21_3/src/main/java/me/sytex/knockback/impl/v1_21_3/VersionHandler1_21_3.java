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

package me.sytex.knockback.impl.v1_21_3;

import java.util.Collection;
import me.sytex.knockback.common.VersionHandler;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class VersionHandler1_21_3 implements VersionHandler {

  @Override
  public void handle(@NotNull Location location) {
    for (LivingEntity livingEntity : location.getNearbyLivingEntities(12)) {
      AttributeInstance attributeInstance = livingEntity.getAttribute(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE);

      if (attributeInstance == null) continue;

      Collection<AttributeModifier> modifiers = attributeInstance.getModifiers();

      if (modifiers.isEmpty()) continue;

      double value = modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
      double reduction = value > 0.6 ? 0.6 - value : 0.0;

      attributeInstance.setBaseValue(reduction);
    }
  }
}
