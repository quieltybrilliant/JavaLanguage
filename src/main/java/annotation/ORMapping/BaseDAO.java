package annotation.ORMapping;

import java.io.Serializable;

public interface BaseDAO {
    <T> Serializable save(T t);
}
