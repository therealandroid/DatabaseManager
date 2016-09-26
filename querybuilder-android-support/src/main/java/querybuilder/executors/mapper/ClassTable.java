package querybuilder.executors.mapper;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class ClassTable<T> {
    private String prefix;
    private Class<T> clazz;

    public ClassTable(String prefix, Class clazz) {
        this.prefix = prefix;
        this.clazz = clazz;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
