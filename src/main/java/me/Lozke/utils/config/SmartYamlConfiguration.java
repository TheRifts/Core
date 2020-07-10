package me.Lozke.utils.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * An extension of {@link YamlConfiguration} that can load and save itself.
 */
public class SmartYamlConfiguration extends YamlConfiguration implements SmartConfiguration {

    private File file;

    /**
     * Instantiates a new SmartYamlConfiguration with a selected {@link File} to load/save from/to and
     * automatically loads the file.
     *
     * @param file file to load/save from/to
     */
    public SmartYamlConfiguration(File file) {
        this(file, '.');
    }

    /**
     * Instantiates a new SmartYamlConfiguration with a selected {@link File} to load/save from/to and
     * automatically loads the file.
     *
     * @param file file to load/save from/to
     * @param separator separator char
     */
    public SmartYamlConfiguration(File file, char separator) {
        super();
        this.file = file;
        options().pathSeparator(separator);
        load();
    }


    public SmartYamlConfiguration(Configuration configuration) {
        super();
        this.file = null;
        options().pathSeparator(configuration.options().pathSeparator());
    }

    public SmartYamlConfiguration() {
        super();
        this.file = null;
    }

    /**
     * Loads from the file passed into the constructor.
     *
     * Equivalent of using {@link #load(File)} on a {@link File}.
     */
    @Override
    public void load() {
        try {
            load(this.file);
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Saves to the file passed into the constructor.
     *
     * Equivalent of using {@link #save(File)} on a {@link File}.
     */
    @Override
    public void save() {
        try {
            save(this.file);
        } catch (Exception e) {
            // do nothing
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String getFileName() {
        return file != null ? file.getName() : "";
    }

}
