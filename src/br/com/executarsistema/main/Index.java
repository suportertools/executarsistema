package br.com.executarsistema.main;

import br.com.executarsistema.seguranca.Conf;
import br.com.executarsistema.sistema.conf.ConfWebService;
import br.com.executarsistema.utils.Block;
import br.com.executarsistema.utils.BlockInterface;
import br.com.executarsistema.utils.Logs;
import br.com.executarsistema.utils.Mac;
import br.com.executarsistema.utils.WebService;
import br.com.executarsistema.webservice.classes.WSExecutarSistema;
import br.com.executarsistema.webservice.classes.WSStatus;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Index extends JFrame {

    public static void main(String args[]) {
        Conf conf = new Conf();
        ConfWebService cws = new ConfWebService();
        conf.loadJson();
        URL url;
        try {
            url = new URL(cws.getCurl());
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", "client=" + cws.getClient() + "; user=" + cws.getUser() + "; mac=" + Mac.getInstance() + "");
            conn.connect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        Block.TYPE = "" + 1;
        if (!Block.registerInstance()) {
            // instance already running.
            System.out.println("Another instance of this application is already running.  Exiting.");
            JOptionPane.showMessageDialog(null, "Aplicação já esta em execução");
            System.exit(0);
        }
        Block.setBlockInterface(new BlockInterface() {
            @Override
            public void newInstanceCreated() {
                System.out.println("New instance detected...");
                // this is where your handler code goes...
            }
        });
        new Index().setVisible(false);
    }

    public Index() {
        try {
            Conf conf = new Conf();
            conf.loadJson();
            String result = WebService.GET("autenticar_dispositivo.jsf", "", "");
            Gson gson = new Gson();
            WSStatus wSStatus = gson.fromJson(result, WSStatus.class);
            if (wSStatus.getCodigo() != 0) {
                JOptionPane.showMessageDialog(null,
                        wSStatus.getDescricao(),
                        "Validação",
                        JOptionPane.WARNING_MESSAGE);
                Logs logs = new Logs();
                logs.save("index", wSStatus.getDescricao());
                System.exit(0);
                return;
            }
            String mac = Mac.getInstance();
            result = WebService.GET("executar_sistema.jsf", "", "");
            WSExecutarSistema executarSistema = gson.fromJson(result, WSExecutarSistema.class);
            String url = "";
            url += "\"" + conf.getExecutable() + "\" ";
            if (!conf.getExecutable().contains("firefox")) {
                url += " " + conf.getParans() + " ";
            }
            if (conf.getApp_browser()) {
                if (!conf.getExecutable().contains("firefox")) {
                    url += " --app=\"" + executarSistema.getUrl() + "?filial=" + mac + "\"";
                } else {
                    url += " \"" + executarSistema.getUrl() + "?filial=" + mac + "\"";
                }
            } else {
                url += " \"" + executarSistema.getUrl() + "?filial=" + mac + "\"";
            }
            Runtime.getRuntime().exec(url);
        } catch (JsonSyntaxException | IOException e) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
        }
        System.exit(0);
    }

    public void close() {
        super.dispose();
    }

    public class Status {

        private Integer status_code;
        private String status_details;

        public Status() {
            this.status_code = 0;
            this.status_details = "";
        }

        public Status(Integer status_code, String status_details) {
            this.status_code = status_code;
            this.status_details = status_details;
        }

        public Integer getStatus_code() {
            return status_code;
        }

        public void setStatus_code(Integer status_code) {
            this.status_code = status_code;
        }

        public String getStatus_details() {
            return status_details;
        }

        public void setStatus_details(String status_details) {
            this.status_details = status_details;
        }
    }

}
