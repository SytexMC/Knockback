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

import static org.bukkit.Bukkit.getPluginManager;

import me.sytex.knockback.common.VersionHandler;
import me.sytex.knockback.listeners.BlockExplodeListener;
import me.sytex.knockback.listeners.EntityExplodeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Knockback extends JavaPlugin {

  public static VersionHandler handler;

  @Override
  public void onEnable() {
    handler = VersionSupport.initialize();

    getPluginManager().registerEvents(new BlockExplodeListener(), this);
    getPluginManager().registerEvents(new EntityExplodeListener(), this);
  }
}
