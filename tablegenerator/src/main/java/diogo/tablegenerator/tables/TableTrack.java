package diogo.tablegenerator.tables;


import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.ForeignKey;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;

/**
 * Created by diogojayme on 8/4/16.
 */
@Table(name = "track")
public class TableTrack {

    @PrimaryKey
    private long id;

    @Column(name = "name", size = 200)
    private String name;

    @ForeignKey(name = "album_id", references = TableAlbum.class, foreignKey = "id_alias") // ID have to be the same as the declared in the referenced table
    private int albumId;

}
