package br.com.executarsistema.utils;

import java.io.File;
import java.util.Properties;

public class Property {

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getAppData() {
        return System.getenv("APPDATA");
    }

    public static String getTemp() {
        return System.getProperty("java.io.tmpdir");
    }

    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    public static String getProgramFiles() {
        return programFiles(getOSArch());
    }

    public static String getProgramFiles(String arch) {
        File f = new File(programFiles(arch));
        if (f.exists()) {
            return f.getPath();
        }
        return null;
    }

    public static String getProgramFilesX86() {
        File f = new File(programFiles("x86"));
        if (f.exists()) {
            return f.getPath();
        }
        return null;
    }

    public static String getProgramFilesX64() {
        File f = new File(programFiles("x64"));
        if (f.exists()) {
            return f.getPath();
        }
        return null;
    }

    public static String programFiles(String arch) {
        String osArch = arch;
        String programaFiles = "";
        if (osArch.equals("x86")) {
            if (getOSName().toLowerCase().contains("windows")) {
                programaFiles = "C:\\Program Files (x86)\\";
            }
        } else if (osArch.equals("x64")) {
            if (getOSName().toLowerCase().contains("windows")) {
                programaFiles = "C:\\Program Files\\";
            }
        }
        return programaFiles;
    }

}
