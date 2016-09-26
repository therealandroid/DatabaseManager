package diogo.tablegenerator;


import diogo.tablegenerator.processor.TableGenerator;
import diogo.tablegenerator.tables.TableAlbum;
import diogo.tablegenerator.tables.TableArtist;
import diogo.tablegenerator.tables.TableTrack;
import querybuilder.configurations.Configuration;
import querybuilder.configurations.Database;

/**
 * Created by diogojayme on 8/4/16.
 */
public class Main {

    public static void main(String[] args){
        Configuration.setDatabase(Database.SQLITE);

        String CREATE_TABLE_ALBUM = TableGenerator.from(TableAlbum.class);
        String CREATE_TABLE_ARTIST = TableGenerator.from(TableArtist.class);
        String CREATE_TABLE_TRACK = TableGenerator.from(TableTrack.class);

        System.out.println(CREATE_TABLE_ALBUM);
        System.out.println(CREATE_TABLE_ARTIST);
        System.out.println(CREATE_TABLE_TRACK);
    }


}
