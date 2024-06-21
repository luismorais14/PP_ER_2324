package project;

import com.estg.core.ContainerType;

import java.util.Arrays;
import java.util.Objects;

public class ContainerTypeImpl implements ContainerType {
    private String[] types;


    //verificar o .deepEquals
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ContainerTypeImpl other = (ContainerTypeImpl) o;
        return Objects.deepEquals(types, other.types);
    }
}
