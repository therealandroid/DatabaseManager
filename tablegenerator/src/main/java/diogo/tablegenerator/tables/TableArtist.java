package diogo.tablegenerator.tables;


import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;

/**
 * Created by diogojayme on 8/4/16.
 */

@Table(name = "artist")
public class TableArtist {

    @PrimaryKey(name = "id")
    private long id;

    @Column(name = "name", size = 200)
    private String name;

    @Column(name = "url")
    private String url;


}
