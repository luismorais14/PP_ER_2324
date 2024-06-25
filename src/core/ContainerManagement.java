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

import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;

public class ContainerManagement {
    private final int ARRAY_SIZE = 10;

    private Container[] containers;
    private static int nContainers = 0;

    /**
     * Método construtor de ContainerManagement
     */
    public ContainerManagement() {
        containers = new Container[ARRAY_SIZE];
    }

    /**
     * Método responsável por especificar a coleção de containers
     * @param containers a coleção de containers
     */
    public void setContainers(Container[] containers) {
        this.containers = containers;
    }

    /**
     * Método responsável por retornar o número de containers na coleção
     * @return o número de containers na coleção
     */
    public int getnContainers() {
        return nContainers;
    }

    /**
     * Método responsável por retornar a coleção de containers
     * @return a coleção de containers
     */
    public Container[] getContainers() {
        return this.containers;
    }

    /**
     * Método responsável por adicionar um container à coleção de containers
     *
     * @param cntnr o container a ser adicionado
     * @return o sucesso ou insucesso da operação
     */
    public boolean addContainer(Container cntnr) {
        if (cntnr == null) {
            return false;
        }
        if (nContainers == this.containers.length - 1) {
            expandContainerArray();
        }

        if (checkContainerExistence(cntnr)) {
            return false;
        } else {
            this.containers[nContainers] = cntnr;
            nContainers++;
        }
        return true;
    }

    /**
     * Método responsável por remover um container da coleção
     * @param cntnr o container a ser removido
     * @throws AidBoxException se o container recebido como parâmetro não existir
     */
    public void removeContainer(Container cntnr) throws AidBoxException {
        int index = this.findContainer(cntnr);
        if (cntnr == null) {
            throw new AidBoxException("Container is null");
        }
        if (index == nContainers) {
            this.containers[index] = null;
            nContainers--;
        } else {
            for (int i = index; i < this.containers.length - index - 1; i++) {
                this.containers[i] = this.containers[i + 1];
            }
            nContainers--;
        }
    }

    /**
     * Método responsável por encontar a posição do contentor recebido como parâmetro no array de containers
     *
     * @param container o container a ser encontrada a posição
     * @return a posição do container na coleção
     */
    private int findContainer(Container container) {
        int index = -1;
        for (int i = 0; i < this.containers.length; i++) {
            if (this.containers[i].equals(container)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * método responsável por aumentar o tamanho da coleção de containers
     */
    public void expandContainerArray() {
        Container[] newArray = new Container[this.containers.length + 5];
        for (int i = 0; i < nContainers; i++) {
            newArray[i] = containers[i];
        }
        this.containers = newArray;
    }

    /**
     * Método responsável por verificar se um container já existe na coleção
     *
     * @param cntnr container a verificar
     * @return o sucesso ou insucesso da operação
     */
    public boolean checkContainerExistence(Container cntnr) {
        boolean aux = false;
        for (Container container1 : this.containers) {
            if (container1 != null && cntnr != null) {
                if (container1.getCode().compareTo(cntnr.getCode()) == 0) {
                    aux = true;
                }
            }
        }
        return aux;
    }

    /**
     * Método responsável por verificar se já existe no array um container do tipo do container recebido como parâmetro
     * @param container o container a verificar se existe um daquele tipo
     * @return o sucesso ou insucesso da operação
     */
    public boolean verifyContainerType(Container container) {
        if (container == null) {
            return false;
        }

        for (int i = 0; i < this.containers.length; i++) {
            ContainerImpl tempContainer = (ContainerImpl) this.containers[i];
            if (tempContainer != null) {
                if (tempContainer.verifyContainerType(container)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < this.containers.length; i++) {
            if (this.containers[i] != null) {
                txt += this.containers[i].toString() + "\n";
            }
        }
        return txt;
    }
}
