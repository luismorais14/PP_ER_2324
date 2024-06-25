package management;

import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;

public class PickingMapManagement {
    private final int ARRAY_SIZE = 10;

    private PickingMap[] pickingMaps;
    private static int nPickingMaps = 0;

    /**
     * Construtor de PickingMapManagement
     */
    public PickingMapManagement() {
        this.pickingMaps = new PickingMap[ARRAY_SIZE];
    }

    /**
     * Método responsável por especificar a coleção de PickingMaps
     * @param pickingMaps a coleção de pickingMaps
     */
    public void setPickingMaps(PickingMap[] pickingMaps) {
        this.pickingMaps = pickingMaps;
    }

    /**
     * Método responsável por retornar a coleção de PickingMaps
     * @return a coleção de pickingMaps
     */
    public PickingMap[] getPickingMaps() {
        return this.pickingMaps;
    }

    /**
     * Método responsável por retornar o número de PickingMaps na coleção
     * @return o número de pickingMaps na coleção
     */
    public static int getNPickingMaps() {
        return nPickingMaps;
    }

    /**
     * Método responsável por adicionar um pickingMap à coleção
     * @param pickingMap pickingMap a ser adicionado à coleção
     * @return o sucesso ou insucesso da operação
     */
    public boolean addPickingMap(PickingMap pickingMap) {
        if (pickingMap == null) {
            return false;
        }
        if (this.checkPickingMapExistence(pickingMap)) {
            return false;
        }
        if (nPickingMaps == this.pickingMaps.length - 1) {
            this.expandPickingMapArraySize();
        } else {
            this.pickingMaps[nPickingMaps] = pickingMap;
            nPickingMaps++;
        }
        return true;
    }

    /**
     * Método responsável por remover um pickingMap da coleção
     * @param pickingMap o pickingMap a ser removido
     * @throws PickingMapException se o pickingMap recebido como parâmetro não existir
     */
    public void removePickingMap(PickingMap pickingMap) throws PickingMapException {
        int index = this.findPickingMap(pickingMap);
        if (pickingMap == null) {
            throw new PickingMapException("Container is null");
        }
        if (index == nPickingMaps) {
            this.pickingMaps[index] = null;
            nPickingMaps--;
        } else {
            for (int i = index; i < this.pickingMaps.length - index - 1; i++) {
                this.pickingMaps[i] = this.pickingMaps[i + 1];
            }
            nPickingMaps--;
        }
    }

    /**
     * Método responsável por encontar a posição do pickingMap recebido como parâmetro no array de pickingMaps
     *
     * @param pickingMap o container a ser encontrada a posição
     * @return a posição do container na coleção
     */
    private int findPickingMap(PickingMap pickingMap) {
        int index = -1;
        for (int i = 0; i < this.pickingMaps.length; i++) {
            if (this.pickingMaps[i].equals(pickingMap)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Método resposável por expandir a capacidade do array de picking maps
     */
    private void expandPickingMapArraySize() {
        PickingMap[] newArray = new PickingMap[ARRAY_SIZE + 5];
        for (int i = 0; i < nPickingMaps; i++) {
            newArray[i] = this.pickingMaps[i];
        }
        this.pickingMaps = newArray;
    }

    /**
     * Método responsável por verificar a existência de um pickingmap na coleção
     * @param pickingMap o pickingMap a ser verificado
     * @return o sucesso ou insucesso da operação
     */
    private boolean checkPickingMapExistence(PickingMap pickingMap) {
        if (pickingMap == null) {
            return false;
        }
        for (int i = 0; i < nPickingMaps; i++) {
            if (pickingMap.equals(this.pickingMaps[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < this.pickingMaps.length; i++) {
            if (this.pickingMaps[i] != null) {
                txt += this.pickingMaps[i].toString() + "\n";
            }
        }
        return txt;
    }
}
