package br.com.executarsistema.seguranca;

import br.com.executarsistema.financeiro.Caixa;
import br.com.executarsistema.pessoa.Filial;
import br.com.executarsistema.seguranca.dao.MacFilialDao;
import br.com.executarsistema.utils.Mac;
import javax.persistence.*;

@Entity
@Table(name = "seg_mac_filial",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_departamento", "id_filial", "ds_mac", "nr_mesa"})
)
public class MacFilial implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_departamento", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Departamento departamento;
    @JoinColumn(name = "id_filial", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Filial filial;
    @Column(name = "ds_mac", nullable = false)
    private String mac;
    @Column(name = "nr_mesa")
    private Integer mesa;
    @JoinColumn(name = "id_caixa", referencedColumnName = "id")
    @ManyToOne
    private Caixa caixa;
    @Column(name = "ds_descricao", nullable = true, length = 100)
    private String descricao;
    @Column(name = "is_caixa_operador", columnDefinition = "boolean default true")
    private boolean caixaOperador;

    public MacFilial() {
        this.id = null;
        this.departamento = new Departamento();
        this.filial = new Filial();
        this.mac = "";
        this.mesa = 0;
        this.caixa = new Caixa();
        this.descricao = "";
        this.caixaOperador = true;
    }

    public MacFilial(Integer id, Departamento departamento, Filial filial, String mac, Integer mesa, Caixa caixa, String descricao, boolean caixaOperador) {
        this.id = id;
        this.departamento = departamento;
        this.filial = filial;
        this.mac = mac;
        this.mesa = mesa;
        this.caixa = caixa;
        this.descricao = descricao;
        this.caixaOperador = caixaOperador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isCaixaOperador() {
        return caixaOperador;
    }

    public void setCaixaOperador(boolean caixaOperador) {
        this.caixaOperador = caixaOperador;
    }

    public static MacFilial getAcessoFilial() {
        String mac = Mac.getInstance();
        MacFilialDao macFilialDao = new MacFilialDao();
        MacFilial mf = macFilialDao.findMacFilial(mac);
        if (mf == null) {
            return null;
        }
        return mf;
    }
}
