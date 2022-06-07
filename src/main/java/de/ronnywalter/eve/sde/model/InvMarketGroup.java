package de.ronnywalter.eve.sde.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invMarketGroups")
public class InvMarketGroup {
    @Id
    @Column(name = "marketGroupID", nullable = false)
    private Integer id;

    @Column(name = "parentGroupID")
    private Integer parentGroupID;

    @Column(name = "marketGroupName", length = 100)
    private String marketGroupName;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "iconID")
    private Integer iconID;

    @Column(name = "hasTypes")
    private Boolean hasTypes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentGroupID() {
        return parentGroupID;
    }

    public void setParentGroupID(Integer parentGroupID) {
        this.parentGroupID = parentGroupID;
    }

    public String getMarketGroupName() {
        return marketGroupName;
    }

    public void setMarketGroupName(String marketGroupName) {
        this.marketGroupName = marketGroupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIconID() {
        return iconID;
    }

    public void setIconID(Integer iconID) {
        this.iconID = iconID;
    }

    public Boolean getHasTypes() {
        return hasTypes;
    }

    public void setHasTypes(Boolean hasTypes) {
        this.hasTypes = hasTypes;
    }

}