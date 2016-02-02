package br.com.executarsistema.main;

import br.com.executarsistema.seguranca.Conf;
import br.com.executarsistema.utils.Block;
import br.com.executarsistema.utils.BlockInterface;
import br.com.executarsistema.utils.Logs;
import br.com.executarsistema.utils.WebService;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class Index extends JFrame {

    public static void main(String args[]) {
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
        Object result = WebService.GET("autenticar_dispositivo.jsf", "", "");
        Status s = (Status) result;
        if (s.getStatus_code() != 0) {
            JOptionPane.showMessageDialog(null,
                    s.getStatus_details(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            Logs logs = new Logs();
            logs.save("index", s.getStatus_details());
            System.exit(0);
            return;
        }
//        LiberaAcessoDao liberaAcessoDao = new LiberaAcessoDao();
//        liberaAcessoDao.clear();
//        LiberaAcesso liberaAcesso = liberaAcessoDao.findByMac(macFilial);
//        if (liberaAcesso == null) {
//            Date date = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            cal.add(Calendar.MINUTE, 3);
//            Date expirationDate = cal.getTime();
//            liberaAcesso = new LiberaAcesso();
//            liberaAcesso.setExpira(expirationDate);
//            liberaAcesso.setMacFilial(macFilial);
//            if (!new Dao().save(liberaAcesso, true)) {
//                Logs logs = new Logs();
//                logs.save("index", "Erro ao salvar liberação de acesso!");
//                JOptionPane.showMessageDialog(null,
//                        "Ao salvar liberação de acesso!",
//                        "Erro",
//                        JOptionPane.WARNING_MESSAGE);
//                System.exit(0);
//            }
//        }
        try {
//            String url = "";
//            String context = "";
//            url += "\"" + conf.getExecutable() + "\" ";
//            if (!conf.getExecutable().contains("firefox")) {
//                url += " " + conf.getParans() + " ";
//            }
//            if (!conf.getContext().isEmpty()) {
//                context = conf.getContext() + "/";
//            }
//            if (conf.getApp_browser()) {
//                if (!conf.getExecutable().contains("firefox")) {
//                    url += " --app=\"http://" + conf.getUrl() + context + "?filial=" + mac + "\"";
//                } else {
//                    url += " \"http://" + conf.getUrl() + context + "?filial=" + mac + "\"";
//                }
//            } else {
//                url += " \"http://" + conf.getUrl() + context + "?filial=" + mac + "\"";
//            }
            // Runtime.getRuntime().exec(url);
        } catch (Exception e) {
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
