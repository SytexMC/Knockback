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

package me.sytex.knockback.listeners;

import me.sytex.knockback.handlers.ExplosionHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.jetbrains.annotations.NotNull;

public class EntityExplodeListener implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onEntityExplode(@NotNull ExplosionPrimeEvent event) {
    if (event.getEntity().getType() != EntityType.END_CRYSTAL) return;

    ExplosionHandler.handle(event.getEntity().getLocation());
  }
}
