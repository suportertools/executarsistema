package br.com.executarsistema.seguranca;

import br.com.executarsistema.dao.DB;
import java.util.List;
import javax.persistence.Query;

public class LiberaAcessoDao extends DB {

    public LiberaAcesso findByMac(MacFilial mf) {
        try {
            Query query = getEntityManager().createQuery(" SELECT LA FROM LiberaAcesso AS LA WHERE LA.macFilial.id = :mac_filial_id AND LA.expira <= CURRENT_TIMESTAMP");
            query.setParameter("mac_filial_id", mf.getId());
            List list = query.getResultList();
            if (!query.getResultList().isEmpty() && list.size() == 1) {
                return (LiberaAcesso) list.get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public LiberaAcesso findByMac(String mac) {
        try {
            Query query = getEntityManager().createQuery(" SELECT LA FROM LiberaAcesso AS LA WHERE LA.macFilial.mac = :mac AND LA.expira <= CURRENT_TIMESTAMP");
            query.setParameter("mac", mac);
            List list = query.getResultList();
            if (!query.getResultList().isEmpty() && list.size() == 1) {
                return (LiberaAcesso) list.get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Boolean clear() {
        try {
            getEntityManager().getTransaction().begin();
            Query query = getEntityManager().createNativeQuery(" DELETE FROM seg_libera_acesso WHERE dt_expira > CURRENT_TIMESTAMP ");
            if (query.executeUpdate() == 0) {
                getEntityManager().getTransaction().rollback();
                return false;
            }
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            return false;
        }
        return true;
    }
}
