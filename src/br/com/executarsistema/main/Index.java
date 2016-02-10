package br.com.executarsistema.main;

import br.com.executarsistema.seguranca.Conf;
import br.com.executarsistema.utils.Block;
import br.com.executarsistema.utils.BlockInterface;
import br.com.executarsistema.utils.CopyFilesConf;
import br.com.executarsistema.utils.Logs;
import br.com.executarsistema.utils.Mac;
import br.com.executarsistema.utils.UpdateJar;
import br.com.executarsistema.webservice.classes.WSExecutarSistema;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rtools.WebService;

public final class Index extends JFrame {

    public static void main(String args[]) {
        new CopyFilesConf().load();
        new UpdateJar().execute();
        if (!WebService.existConnection()) {
            int resposta;
            resposta = JOptionPane.showConfirmDialog(null, "Erro ao conectar ao dispositivo!", "Mensagem do Programa", JOptionPane.CLOSED_OPTION);
            if (resposta == 0) {
                System.exit(0);
            }
            System.exit(0);
        }
        Conf conf = new Conf();
        conf.loadJson();
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
            WebService webService = new WebService();
            webService.GET("autenticar_dispositivo");
            try {
                webService.execute();
            } catch (Exception ex) {
                // Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
            }
            Gson gson = new Gson();
            if (webService.wSStatus().getCodigo() != 0) {
                JOptionPane.showMessageDialog(null,
                        webService.wSStatus().getDescricao(),
                        "Validação",
                        JOptionPane.WARNING_MESSAGE);
                Logs logs = new Logs();
                logs.save("index", webService.wSStatus().getDescricao());
                System.exit(0);
                return;
            }
            Conf conf = new Conf();
            conf.loadJson();
            String mac = Mac.getInstance();
            webService.GET("executar_sistema");
            try {
                webService.execute();
            } catch (Exception ex) {
                // Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
            }
            WSExecutarSistema executarSistema = (WSExecutarSistema) webService.object(new WSExecutarSistema());
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
