package de.ronnywalter.eve.sde.model;

import javax.persistence.*;

@Entity
@Table(name = "eveIcons")
public class EveIcon {
    @Id
    @Column(name = "iconID", nullable = false)
    private Integer id;

    @Column(name = "iconFile", length = 500)
    private String iconFile;

    @Lob
    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIconFile() {
        return iconFile;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}