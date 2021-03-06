package io.github.carlosthe19916.pe.models.jpa.entities;

import io.github.carlosthe19916.core.models.jpa.entities.OrganizationEntity;
import io.github.carlosthe19916.pe.models.EstadoComprobantePago;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nota", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serie", "numero", "organization_id"})
})
@NamedQueries(value = {
        @NamedQuery(name = "getNotasEmpezandoPorLasMasRecientes", query = "select n from NotaEntity n  inner join n.organization o where o.id=:organizationId and n.codigoTipoComprobante=:codigo order by n.createdAt"),
        @NamedQuery(name = "getNotasConSerieEmpezandoPorLasMasRecientes", query = "select n from NotaEntity n inner join n.organization o where o.id=:organizationId and n.codigoTipoComprobante=:codigo and n.serie=:serie order by n.createdAt"),
        @NamedQuery(name = "getNotasPorSerieYNumero", query = "select n from NotaEntity n inner join n.organization o where o.id=:organizationId and n.serie=:serie and n.numero=:numero"),
        @NamedQuery(name = "countNotas", query = "select count(n) from NotaEntity n inner join n.organization o where o.id=:organizationId"),
        @NamedQuery(name = "getNotasPorId", query = "select n from NotaEntity n inner join n.organization o where o.id=:organizationId and n.id=:notaId"),
        @NamedQuery(name = "filterDistinctNotas", query = "select distinct n from NotaEntity n inner join n.organization o where o.id=:organizationId and lower(n.serie) like :filterText"),
})
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "graph.ListNotas", attributeNodes = {
                @NamedAttributeNode(value = "id"),
                @NamedAttributeNode(value = "datosVenta", subgraph = "datosVenta"),
                @NamedAttributeNode(value = "estadoSunat", subgraph = "estadoSunat"),
                @NamedAttributeNode(value = "organization", subgraph = "organization"),
        }, subgraphs = {
                @NamedSubgraph(name = "datosVenta", attributeNodes = {
                        @NamedAttributeNode(value = "id")
                }),
                @NamedSubgraph(name = "estadoSunat", attributeNodes = {
                        @NamedAttributeNode(value = "id")
                }),
                @NamedSubgraph(name = "organization", attributeNodes = {
                        @NamedAttributeNode(value = "id")
                })
        })
})
public class NotaEntity {

    @Id
    @Access(AccessType.PROPERTY)
    @Column(name = "id")
    private String id;

    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "serie")
    private String serie;

    @NotNull
    @Column(name = "numero")
    private int numero;

    @NotNull
    @Column(name = "codigo_tipo_comprobante")
    private String codigoTipoComprobante;

    @NotNull
    @Column(name = "tipo_nota")
    private String tipoNota;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "invoice_afectado_id")
    private InvoiceEntity invoiceAfectado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoComprobantePago estado;

    @Column(name = "file_id")
    private String fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "cdr_id")
    private CdrEntity cdr;

    @NotNull
    @Type(type = "org.hibernate.type.YesNoType")
    @Column(name = "enviar_sunat")
    private boolean enviarSunat;

    @NotNull
    @Type(type = "org.hibernate.type.YesNoType")
    @Column(name = "enviar_cliente")
    private boolean enviarCliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "datos_venta_id")
    private DatosVentaEntity datosVenta;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "estado_sunat_id")
    private EstadoSunatEntity estadoSunat;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "organization_id")
    private OrganizationEntity organization;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Version
    @Column(name = "version")
    private int version;

    @ElementCollection
    @Column(name = "value")
    @CollectionTable(name = "nota_labels", joinColumns = {@JoinColumn(name = "invoice_id")})
    private Set<String> labels = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCodigoTipoComprobante() {
        return codigoTipoComprobante;
    }

    public void setCodigoTipoComprobante(String codigoTipoComprobante) {
        this.codigoTipoComprobante = codigoTipoComprobante;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public InvoiceEntity getInvoiceAfectado() {
        return invoiceAfectado;
    }

    public void setInvoiceAfectado(InvoiceEntity invoiceAfectado) {
        this.invoiceAfectado = invoiceAfectado;
    }

    public EstadoComprobantePago getEstado() {
        return estado;
    }

    public void setEstado(EstadoComprobantePago estado) {
        this.estado = estado;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public CdrEntity getCdr() {
        return cdr;
    }

    public void setCdr(CdrEntity cdr) {
        this.cdr = cdr;
    }

    public boolean isEnviarSunat() {
        return enviarSunat;
    }

    public void setEnviarSunat(boolean enviarSunat) {
        this.enviarSunat = enviarSunat;
    }

    public boolean isEnviarCliente() {
        return enviarCliente;
    }

    public void setEnviarCliente(boolean enviarCliente) {
        this.enviarCliente = enviarCliente;
    }

    public DatosVentaEntity getDatosVenta() {
        return datosVenta;
    }

    public void setDatosVenta(DatosVentaEntity datosVenta) {
        this.datosVenta = datosVenta;
    }

    public EstadoSunatEntity getEstadoSunat() {
        return estadoSunat;
    }

    public void setEstadoSunat(EstadoSunatEntity estadoSunat) {
        this.estadoSunat = estadoSunat;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }
}
