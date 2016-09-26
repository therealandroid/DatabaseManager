package diogo.tablegenerator.tables;


import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.ForeignKey;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;

/**
 * Created by diogojayme on 8/4/16.
 */

@Table(name = "album")
public class TableAlbum {

    @PrimaryKey(name = "id_alias")
    private long id;

    @Column(name = "name", size = 200)
    private String name;

    @Column(name = "offline")
    private boolean offline;

    @Column(name = "state", size = 1)
    private int state;

    @ForeignKey(name = "artist_id", references = TableArtist.class)
    private int artistId;
}