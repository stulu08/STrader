package de.stulu.strader.util;

import de.stulu.strader.main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private File file;
    public YamlConfiguration config;
    public String name = "null";
    public Configuration(String name){
        File dir = new File("./plugins/Stulu-Trader/");
        if (!dir.exists())
            dir.mkdirs();

        file = new File(dir,name+ ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                main.instance.SendConsoleMessage(main.getPrefix() + "Config file " + name + ".yml couldn't created(KEIN ERROR vlt nur weil es in ein seperaten order ist)");
            }
        }
        this.name = name;
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void addDefault(String path, Object value){
        config.addDefault(path,value);
    }
    public boolean contains(String path){
        return config.contains(path);
    }

    public void set(String path, Object value){
        config.set(path,value);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object get(String path){
        if(!contains(path)){
            return null;
        }
        return config.get(path);
    }
    public boolean getBool(String path){
        if(!contains(path)){
            return false;
        }
        return config.getBoolean(path);
    }
    public Map<String, Object> getValues(boolean deep){
        return config.getValues(deep);
    }
    public SMap<String, String> getValuesAsString(boolean deep){
        Map<String,Object> map = config.getValues(deep);
        SMap<String,String> newMap = new SMap<String,String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof String){
                newMap.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return newMap;
    }

}
