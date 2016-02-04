package br.com.executarsistema.utils;

import br.com.executarsistema.sistema.conf.ConfWebService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UpdateJar {

    public void execute() {
//        ConfWebService confWebService = new ConfWebService();
//        confWebService.loadJson();
//        Version v = new Version();
//        Boolean update = false;
//        try {
//            URL url = new URL(confWebService.getCurlFiles() + "version.json");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
//            // 15 Segundos
//            con.setConnectTimeout(15000);
//            con.setUseCaches(true);
//            String result = "";
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
//                result += in.readLine();
//            }
//            Gson gson = new Gson();
//            v = gson.fromJson(result, Version.class);
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            try {
//                Date dataHoje = new Date();
//                Date dataVersao = dateFormat.parse(v.getDate());
//                if(dataHoje.after(dataVersao)) {
//                    int resposta;
//                    resposta = JOptionPane.showConfirmDialog(null, "Nova versão encontrada, deseja atualizar o aplicativo?!", "Nova Versão", JOptionPane.YES_NO_OPTION);
//                    if (resposta == 0) {
//                        update = true;
//                    } else {
//                        return;                        
//                    }
//                    
//                }
//            } catch (ParseException ex) {
//                Logger.getLogger(UpdateJar.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (IOException | JsonSyntaxException e) {
//            System.exit(0);
//        }
//
//        if(!update) {
//            return;
//        }
//
//        // leitor do arquivo a ser baixado
//        InputStream in = null;
//        // conexão com a internete
//        // escritor do arquivo que será baixado
//        OutputStream out = null;
//        System.out.println("Update.download() BAIXANDO " + confWebService.getCurlFiles());
//        URLConnection conn = null;
//        try {
//            URL url = new URL(confWebService.getCurlFiles() + "ExecutarSistema.rar");
//            // verifica se existe proxy
//            if (!"".equals(confWebService.getCurlFiles()) && confWebService.getCurlFiles() != null) {
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(confWebService.getUrl(), confWebService.getPort()));
//                conn = url.openConnection(proxy);
//            } else {
//                conn = url.openConnection();
//            }
//            in = conn.getInputStream();
//            File c = new File(Path.getUserPath() + "\\Rtools\\ExecutarSistema\\Version\\Update\\");
//            c.mkdirs();
//            File f = new File(Path.getRealPath() + "\\");
//            out = new BufferedOutputStream(new FileOutputStream(Path.getUserPath() + "\\Rtools\\ExecutarSistema\\Version\\Update\\ExecutarSistema.jar"));
//            byte[] buffer = new byte[1024];
//            int numRead;
//            long numWritten = 0;
//            while ((numRead = in.read(buffer)) != -1) {
//                out.write(buffer, 0, numRead);
//                numWritten += numRead;
//            }
//            System.out.println("ExecutarSistema.jar" + "\t" + numWritten);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException ioe) {
//            }
//        }
    }

    public class Version {

        private String date;

        public Version() {
            this.date = null;
        }

        public Version(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
