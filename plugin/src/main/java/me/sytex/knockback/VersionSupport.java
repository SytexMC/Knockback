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

package me.sytex.knockback;

import me.sytex.knockback.common.VersionHandler;
import me.sytex.knockback.impl.v1_21.VersionHandler1_21;
import me.sytex.knockback.impl.v1_21_3.VersionHandler1_21_3;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class VersionSupport {

  public static @NotNull VersionHandler initialize() {

    @SuppressWarnings("deprecation")
    int localProtocolVersion = Bukkit.getUnsafe().getProtocolVersion();

    if (localProtocolVersion > 767) {
      return new VersionHandler1_21_3();
    } else {
      return new VersionHandler1_21();
    }
  }
}