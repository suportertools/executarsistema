package br.com.executarsistema.seguranca;

import br.com.executarsistema.utils.Logs;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Conf {

    private String context;
    private String client;
    private String ip;
    private String filial;
    private String app;
    private String key;
    private String user;
    private String password;
    private String method;
    private String action;
    private Integer port;
    private Boolean web_service;
    private Boolean ssl;

    /**
     * Configurações do executavel
     */
    private String executable;
    private String parans;
    private Boolean app_browser;

    public Conf() {
        this.executable = "";
        this.parans = "";
        this.app_browser = false;
    }

    public void loadJson() {
        String path = "";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
        }
        Logs logs = new Logs();
        try {
            File file = new File(path + "\\lib\\conf\\conf.json");
            if (!file.exists()) {
                return;
            }
            String json = null;
            try {
                json = FileUtils.readFileToString(file);
            } catch (IOException ex) {
                Logger.getLogger(Conf.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject jSONObject = new JSONObject(json);
            try {
                executable = jSONObject.getString("executable");
            } catch (Exception e) {
                // logs.save("Conf Erro", "(String) executable: Configuração errada.  Verique o arquivo de configuração (conf)." + e.getMessage());
            }
            try {
                parans = jSONObject.getString("parans");
            } catch (Exception e) {
                logs.save("Conf Erro", "(String) parans: Configuração errada.  Verique o arquivo de configuração (conf)." + e.getMessage());
            }
            try {
                app_browser = jSONObject.getBoolean("app_browser");
            } catch (Exception e) {
                logs.save("Conf Erro", "(String) app_browser: Configuração errada.  Verique o arquivo de configuração (conf)." + e.getMessage());
            }
        } catch (JSONException ex) {
            logs.save("Conf JSONException", ex.getMessage());

        }
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getWeb_service() {
        return web_service;
    }

    public void setWeb_service(Boolean web_service) {
        this.web_service = web_service;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
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

    public Boolean getApp_browser() {
        return app_browser;
    }

    public void setApp_browser(Boolean app_browser) {
        this.app_browser = app_browser;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

}
