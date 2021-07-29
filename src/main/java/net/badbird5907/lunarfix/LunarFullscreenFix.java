package net.badbird5907.lunarfix;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class LunarFullscreenFix {
    public static void main(String[] args) {
        //File optionsLC = new File()
        System.out.println("Finding optionsLC.txt!");
        File file = getOptionsLC(getPathToLC());
        if (file == null){
            System.err.println("Could not find optionsLC.txt in " + getPathToLC());
            System.exit(0);
            return;
        }
        System.out.println("Found optionsLC.txt in " + getPathToLC() + "!");
        System.out.println("Reading json...");
        String json = net.badbird5907.blib.utils.FileUtils.readFileToString(file);
        System.out.println("File contents: " + json);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        System.out.println("Editing...");
        jsonObject.remove("fullscreen");
        jsonObject.addProperty("fullscreen","false"); //boolean is a string for some reason
        String jsonNew = jsonObject.toString();
        System.out.println("Saving...");
        try {
            PrintStream ps = new PrintStream(file);
            ps.print(jsonNew);
            ps.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not save! Error:");
            e.printStackTrace();
            System.exit(1);
            return;
        }
        System.out.println("Done!");
    }
    public static String getPathToLC(){
        if (OS.isWindows()){
            return System.getenv("APPDATA") + "/.minecraft/optionsLC.txt";
        }else if(OS.isMac()){
            return System.getProperty("user.home") + "/Library/Application Support/.minecraft/optionsLC.txt";
        }else if (OS.isUnix()){
            return System.getProperty("user.home") + "/.minecraft/optionsLC.txt";
        }
        return "none";
    }
    public static File getOptionsLC(String path){
        if (path == null){
            return null;
        }
        return new File(path);
    }
}
