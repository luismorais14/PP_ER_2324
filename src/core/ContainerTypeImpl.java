/*
 * Nome: Francisco Morais de Oliveira
 * Número: 8230204
 * Turma: T3
 *
 * Nome: Luís André Nunes Morais
 * Número: 8230258
 * Turma: T3
 */
package core;

import com.estg.core.ContainerType;

public class ContainerTypeImpl implements ContainerType {

    private String type;

    /**
     * Construtor de ContainerTypeImpl
     */
    public ContainerTypeImpl() {
        this.type = "";
    }

    /**
     * Construtor de ContainerTypeImpl
     * @param type o tipo de container
     */
    public ContainerTypeImpl(String type) {
        this.type = type;
    }


    /**
     * Método responsável por retornar o tipo de container
     * @return o tipo de container
     */
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContainerTypeImpl other = (ContainerTypeImpl) o;

        return this.type.compareTo(other.type) == 0;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
