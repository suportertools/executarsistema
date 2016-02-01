package br.com.executarsistema.dao;

import br.com.executarsistema.seguranca.Conf;
import br.com.executarsistema.sistema.Configuracao;
import br.com.executarsistema.utils.DataBase;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import oracle.toplink.essentials.config.CacheType;
import oracle.toplink.essentials.config.TopLinkProperties;
import oracle.toplink.essentials.ejb.cmp3.EntityManagerFactoryProvider;

public class DB {

    private EntityManager entidade;

    public EntityManager getEntityManager() {
        if (entidade == null) {
            Conf conf = new Conf();
            conf.loadJson();
            String databaseName = "";
            databaseName = conf.getClient();
            Configuracao configuracao = servidor(databaseName);
            DataBase dataBase = new DataBase();
            dataBase.loadJson();
            Integer port = 5432;
            if (dataBase.getHost() != null && !dataBase.getHost().isEmpty()) {
                configuracao.setHost(dataBase.getHost());
            }
            if (dataBase.getPort() != null && dataBase.getPort() != 0) {
                port = dataBase.getPort();
            }
            if (!dataBase.getDatabase().isEmpty()) {
                configuracao.setPersistence(dataBase.getDatabase());
            }
            if (!dataBase.getPassword().isEmpty()) {
                configuracao.setSenha(dataBase.getPassword());
            }
            try {
                Map properties = new HashMap();
                properties.put(TopLinkProperties.CACHE_TYPE_DEFAULT, CacheType.NONE);
                // properties.put(TopLinkProperties.CACHE_TYPE_DEFAULT, CacheType.SoftWeak);
                //properties.put(TopLinkProperties.CACHE_TYPE_DEFAULT, CacheType.Full);
                properties.put(TopLinkProperties.JDBC_USER, "postgres");
                properties.put(TopLinkProperties.TRANSACTION_TYPE, "RESOURCE_LOCAL");
                properties.put(TopLinkProperties.JDBC_DRIVER, "org.postgresql.Driver");
                properties.put(TopLinkProperties.JDBC_PASSWORD, configuracao.getSenha());
                properties.put(TopLinkProperties.JDBC_URL, "jdbc:postgresql://" + configuracao.getHost() + ":" + port + "/" + configuracao.getPersistence());
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence", properties);
                String createTable = "";
                if (createTable.equals("criar")) {
                    properties.put(EntityManagerFactoryProvider.DDL_GENERATION, EntityManagerFactoryProvider.CREATE_ONLY);
                }
                entidade = emf.createEntityManager();
            } catch (Exception e) {
                return null;
            }
        }
        return entidade;
    }

    public Configuracao servidor(String cliente) {
        Configuracao configuracao = new Configuracao();
        switch (cliente) {
            case "FundacaoPenteado":
            case "ClinicaIntegrada":
                configuracao.setCaminhoSistema(cliente);
                configuracao.setPersistence("ClinicaIntegrada");
                // configuracao.setPersistence("ClinicaIntegradaProducao");
                configuracao.setHost("192.168.1.60");
                configuracao.setSenha("989899");
                break;
            default:
                break;
        }
        return configuracao;
    }
    // COMÉRCIO LIMEIRA
//    public Configuracao servidor(String cliente) {
//        Configuracao configuracao = new Configuracao();
//        configuracao.setCaminhoSistema(cliente);
//        configuracao.setPersistence(cliente);
//        // IP LOCAL: 192.168.0.201
//        // IP EXTERNO: 200.204.32.23
//        configuracao.setHost("192.168.0.201");
//        configuracao.setSenha("r#@tools");
//        return configuracao;
//    }
    // COMÉRCIO RIBEIRÃO
//    public Configuracao servidor(String cliente) {
//        Configuracao configuracao = new Configuracao();
//        configuracao.setCaminhoSistema("Sindical");
//        configuracao.setHost("localhost");
//        configuracao.setSenha("989899");
//        configuracao.setPersistence(cliente);
//        return configuracao;
//    }
}
