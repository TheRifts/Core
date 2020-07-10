package me.Lozke.utils.config;

import org.bukkit.configuration.Configuration;

import java.io.File;

public interface SmartConfiguration extends Configuration {

    void load();

    void save();

    File getFile();

    String getFileName();

}
