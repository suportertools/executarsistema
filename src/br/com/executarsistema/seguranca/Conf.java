package br.com.executarsistema.seguranca;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Conf {

    private String ip;
    private String port;
    private String project;
    private String client;
    private String executable;
    private String parans;
    private String url;
    private Boolean app;

    public Conf() {
        ip = "";
        port = "";
        project = "";
        client = "";
        executable = "";
        parans = "";
        url = "";
        app = false;
    }

    public Conf(String ip, String port, String project, String client, String executable, String parans, String url, Boolean app) {
        this.ip = ip;
        this.port = port;
        this.project = project;
        this.client = client;
        this.executable = executable;
        this.parans = parans;
        this.app = app;
        this.url = url;
    }

    public void loadJson() {
        String path = "";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
        }
        try {
            File file = new File(path + "\\lib\\conf.json");
            if (!file.exists()) {
                return;
            }
            String json = FileUtils.readFileToString(file);
            JSONObject jSONObject = new JSONObject(json);
            ip = jSONObject.getString("ip");
            client = jSONObject.getString("client");
            port = jSONObject.getString("port");
            project = jSONObject.getString("project");
            executable = jSONObject.getString("executable");
            parans = jSONObject.getString("parans");
            app = jSONObject.getBoolean("app");
            url = jSONObject.getString("url");
        } catch (JSONException ex) {
            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public String getParans() {
        return parans;
    }

    public void setParans(String parans) {
        this.parans = parans;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getApp() {
        return app;
    }

    public void setApp(Boolean app) {
        this.app = app;
    }

}
