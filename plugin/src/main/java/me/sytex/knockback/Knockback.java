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
import me.sytex.knockback.listeners.BlockExplodeListener;
import me.sytex.knockback.listeners.EntityExplodeListener;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class Knockback extends JavaPlugin {

  public static VersionHandler versionHandler;
  public static Knockback plugin;
  public static Server server;

  @Override
  public void onEnable() {
    versionHandler = VersionSupport.initialize();
    plugin = this;
    server = this.getServer();

    Bukkit.getPluginManager().registerEvents(new BlockExplodeListener(), this);
    Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);
  }
}
