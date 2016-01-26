package br.com.executarsistema.main;

import br.com.executarsistema.dao.Dao;
import br.com.executarsistema.seguranca.Conf;
import br.com.executarsistema.seguranca.LiberaAcesso;
import br.com.executarsistema.seguranca.LiberaAcessoDao;
import br.com.executarsistema.seguranca.MacFilial;
import br.com.executarsistema.seguranca.dao.MacFilialDao;
import br.com.executarsistema.utils.Block;
import br.com.executarsistema.utils.BlockInterface;
import br.com.executarsistema.utils.Logs;
import br.com.executarsistema.utils.Mac;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
                System.exit(0);
            }
        });
        Conf conf = new Conf();
        conf.loadJson();
        // String mac = "65-46-54-65-46-54";
        String mac = Mac.getInstance();
        MacFilialDao macFilialDao = new MacFilialDao();
        MacFilial macFilial = macFilialDao.findMacFilial(mac);
        if (macFilial == null) {
            JOptionPane.showMessageDialog(null,
                    "Computador não registrado!",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            Logs logs = new Logs();
            logs.save("index", "Computador não registrado!");
            System.exit(0);
            return;
        }
        LiberaAcessoDao liberaAcessoDao = new LiberaAcessoDao();
        liberaAcessoDao.clear();
        LiberaAcesso liberaAcesso = liberaAcessoDao.findByMac(macFilial);
        if (liberaAcesso == null) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, 3);
            Date expirationDate = cal.getTime();
            liberaAcesso = new LiberaAcesso();
            liberaAcesso.setExpira(expirationDate);
            liberaAcesso.setMacFilial(macFilial);
            if (!new Dao().save(liberaAcesso, true)) {
                Logs logs = new Logs();
                logs.save("index", "Erro ao salvar liberação de acesso!");
                JOptionPane.showMessageDialog(null,
                        "Ao salvar liberação de acesso!",
                        "Erro",
                        JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        }
        try {
            String url = "";
            url += "\"" + conf.getExecutable() + "\" ";
            url += " " + conf.getParans() + " ";
            if (conf.getApp_browser()) {
                url += " --app=\"http://" + conf.getUrl() + "?filial=" + mac + "\"";
            } else {
                url += " \"http://" + conf.getUrl() + "?filial=" + mac + "\"";
            }
            Runtime.getRuntime().exec(url);
        } catch (Exception e) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
        }
        System.exit(0);
    }

    public void close() {
        super.dispose();
    }

}
