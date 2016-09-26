package br.com.diogojayme.databasemanager.table;

import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;

/**
 * Created by diogojayme on 9/13/16.
 */
@Table(name = "university")
public class University {

    @PrimaryKey
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "initials")
    private String initials;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}
