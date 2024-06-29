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

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Vehicle;

public class AidBoxManagement {
    private final int ARRAY_SIZE = 0;

    private AidBox[] aidboxes;
    private static int nAidBoxes = 0;

    /**
     * Construtor de AidBoxManagement
     */
    public AidBoxManagement() {
        aidboxes = new AidBox[ARRAY_SIZE];
    }

    /**
     * Método responsável por especificar a coleção de aidboxes
     *
     * @param aidbox a coleção de aidboxes
     */
    public void setAidBox(AidBox[] aidbox) {
        this.aidboxes = aidbox;
    }

    /**
     * Método responsável por adicionar uma aidbox à coleção
     *
     * @param aidbox a aidbox a ser adicionada
     * @return o sucesso ou insucesso da operação
     */
    public boolean addAidBox(AidBox aidbox) {
        for (int i = 0; i < aidboxes.length; i++) {
            if (nAidBoxes > 0) {
                if (this.aidboxes[i] != null) {
                    AidBoxImpl tmpAidBox = (AidBoxImpl) this.aidboxes[i];
                    if (tmpAidBox.equals(aidbox)) {
                        return false;
                    }
                }
            }
        }
        if (nAidBoxes == this.aidboxes.length) {
            this.expandAidBoxArray();
        }
        this.aidboxes[nAidBoxes] = aidbox;
        nAidBoxes++;
        return true;
    }

    /**
     * Método responsável por remover uma aidbox da coleção
     *
     * @param aidbox a adibox a ser removida
     * @throws AidBoxException exceção a ser lançada caso a aidbox seja null ou não exista
     */
    public void removeAidBox(AidBox aidbox) throws AidBoxException {
        int index = findContainer(aidbox);
        if (aidbox == null) {
            throw new AidBoxException("AidBox is null");
        } else if (index == -1) {
            throw new AidBoxException("AidBox does not exist");
        }
        if (index == nAidBoxes) {
            this.aidboxes[index] = null;
        } else {
            for (int i = index; i < this.aidboxes.length - index - 1; i++) {
                this.aidboxes[i] = this.aidboxes[i + 1];
            }
        }
        nAidBoxes--;
    }

    /**
     * Método responsável por retornar a coleção de aidboxes
     *
     * @return a coleção de aidboxes
     */
    public AidBox[] getAidBoxes() {
        return this.aidboxes;
    }

    /**
     * Verifica se uma AidBox possui containers duplicados do mesmo tipo.
     *
     * @param aidbox a aidbox a ser verificada
     * @return true se houver containers duplicados do mesmo tipo na aidbox, false caso contrário
     */
    public boolean verifyDuplicateContainers(AidBox aidbox) {
        if (aidbox == null) {
            return false;
        }
        Container[] containers = aidbox.getContainers();

        for (int i = 0; i < containers.length; i++) {
            if (containers[i] != null) {
                for (int j = i + 1; j < containers.length - i; j++) {
                    if (containers[j] != null) {
                        if (containers[i].getType().equals(containers[j].getType())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método responsável por encontar a posição da aidbox recebida como parâmetro no array de aidboxes
     *
     * @param aidbox o container a ser encontrada a posição
     * @return a posição do container na coleção
     */
    private int findContainer(AidBox aidbox) {
        int index = -1;
        for (int i = 0; i < this.aidboxes.length; i++) {
            if (this.aidboxes[i].equals(aidbox)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Expande a capacidade do array de aidboxes
     */
    public void expandAidBoxArray() {
        AidBox[] newArray = new AidBox[this.aidboxes.length + 5];
        for (int i = 0; i < nAidBoxes; i++) {
            newArray[i] = this.aidboxes[i];
        }
        this.aidboxes = newArray;
    }

    /**
     * Método responsável por verificar se uma aidbox já existe na coleção
     *
     * @param aidbox aidbox a ser verificada
     * @return o sucesso ou insucesso da operação
     */
    public boolean verifyAidBoxExistence(AidBox aidbox) {
        if (aidbox == null) {
            return false;
        }
        for (int i = 0; i < nAidBoxes; i++) {
            if (this.aidboxes[i].equals(aidbox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica a compatibilidade de uma `AidBox` com o veículo da rota.
     *
     * @param aidbox a `AidBox` cuja compatibilidade deve ser verificada
     * @return true se a `AidBox` for compatível com o veículo da rota, caso contrário, false
     */
    public boolean verifyCompatibility(AidBox aidbox, Vehicle vhcl) {
        boolean aux = false;
        int tmpCounter = 0;
        for (int i = 0; i < aidbox.getContainers().length; i++) {
            if (vhcl.getCapacity(aidbox.getContainers()[i].getType()) != 0.0) {
                tmpCounter++;
            }
        }
        if (tmpCounter != 0) {
            aux = true;
        }

        return aux;
    }

    /**
     * Encontra o índice de uma `AidBox` na coleção
     *
     * @param aidbox a `AidBox` cujo índice deve ser encontrado
     * @return o índice da `AidBox` ou -1 se a `AidBox` não estiver n coleção
     */
    public int indexFounder(AidBox aidbox) {
        int index = -1;
        for (int i = 0; i < this.aidboxes.length; i++) {
            if (this.aidboxes[i].getCode().compareTo(aidbox.getCode()) == 0) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < this.aidboxes.length; i++) {
            AidBoxImpl aidbox = (AidBoxImpl) this.aidboxes[i];
            if (this.aidboxes[i] != null) {
                txt += aidbox.toString() + "\n";
            }
        }
        return txt;
    }
}
