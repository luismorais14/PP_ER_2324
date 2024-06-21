package project;

import com.estg.core.Container;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;

import java.util.Arrays;

public class MeasurementsManagement {
    private final int ARRAY_SIZE = 10;

    private Measurement[] measurements;
    private static int nMeasurements;


    /**
     * Construtor de MeasurementsManagement
     */
    public MeasurementsManagement() {
        measurements = new Measurement[ARRAY_SIZE];
        nMeasurements = 0;
    }

    /**
     * Método responsável por especificar a coleção de measurements
     *
     * @param measurements a coleção de measurements
     */
    public void setMeasurements(Measurement[] measurements) {
        this.measurements = measurements;
    }

    /**
     * Método responsável por retornar o número de measurements existentes na coleção
     *
     * @return o número de measurements
     */
    public static int getNMeasurements() {
        return nMeasurements;
    }

    /**
     * Método responsável por retornar a coleção de measurements
     *
     * @return a coleção de measurements
     */
    public Measurement[] getMeasurements() {
        return this.measurements;
    }

    /**
     * Método responsável por adicionar um measurement à coleção
     *
     * @param measurement a medição a ser adicionada
     * @return o sucesso ou insucesso da operação
     */
    public boolean addMeasurement(Measurement measurement) {
        if (measurement == null) {
            return false;
        }
        if (nMeasurements == this.measurements.length - 1) {
            expandMeasurementsArray();
        }

        this.measurements[nMeasurements] = measurement;
        nMeasurements++;

        return true;
    }

    /**
     * Método responsável por remover uma medição da coleção de medições
     *
     * @param measurement a medição a ser removida
     * @return o sucesso ou insucesso da operação
     */
    public void removeMeasurement(Measurement measurement) throws MeasurementException {
        int index = findMeasurement(measurement);
        if (measurement == null) {
            throw new MeasurementException("Measurement is null.");
        }
        if (index == nMeasurements) {
            this.measurements[index] = null;
            nMeasurements--;
        } else {
            for (int i = index; i < this.measurements.length - index - 1; i++) {
                this.measurements[i] = this.measurements[i + 1];
            }
            nMeasurements--;
        }
    }

    /**
     * Método responsável por expandir o array de measurements
     */
    private void expandMeasurementsArray() {
        Measurement[] newArray = new Measurement[this.measurements.length + 5];
        for (int i = 0; i < nMeasurements; i++) {
            newArray[i] = measurements[i];
        }
        this.measurements = newArray;
    }

    /**
     * Método responsável por encontar a posição da medição recebida como parâmetro no array de medições
     *
     * @param measurement a medição aser encontrada a posição
     * @return a posição da medição
     */
    private int findMeasurement(Measurement measurement) {
        int index = -1;
        for (int i = 0; i < this.measurements.length; i++) {
            if (this.measurements[i].equals(measurement)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Metodo responsável por verificar o valor de uma medição
     *
     * @param measurement uma medição
     * @return retorna o sucesso ou o insucesso da verificação
     */
    public boolean verifyMeasurementValue(Measurement measurement) {
        boolean aux = false;
        if (measurement.getValue() < 0.0) {
            aux = true;
        }
        return aux;
    }

    /**
     * Metodo responsável por verificar se a medição já existe
     *
     * @param positions array com indici da posição das medições no array de meições
     * @param msrmnt    uma medição
     * @return retorna o sucesso ou o insucesso da verificação
     */
    public boolean compareMeasurements(int[] positions, Measurement msrmnt) {
        boolean aux = true;
        for (int i = 0; i < nMeasurements; i++) {
            if (measurements[positions[i]].getValue() == msrmnt.getValue()) {
                aux = false;
            }
        }
        return aux;
    }

    /**
     * Metodo responsável por encontrar medições com data igual
     *
     * @param msrmnt uma medição
     * @return um array de medições com a mesma data da do parametro
     */
    public int[] Searchbydate(Measurement msrmnt) {
        int counter = 0;
        int Temppositions[] = new int[nMeasurements];
        for (int i = 0; i < nMeasurements; i++) {
            if (measurements[i].getDate().isEqual(msrmnt.getDate())) {
                Temppositions[counter] = i;
                counter++;
            }
        }
        int[] positions = new int[counter];
        for (int i = 0; i < counter; i++) {
            positions[i] = Temppositions[i];
        }
        return positions;
    }

    /**
     * Método responsável por mostrar em formato String a coleção de medições
     *
     * @param measurements a coleção de medições a mostrar
     * @return O texto em formato String da coleção de medições
     */
    private String showMeasurements(Measurement[] measurements) {
        if (measurements == null) {
            return "null";
        }
        String result = "";
        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i] != null) {
                result += measurements[i].toString() + "\n";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementsManagement{" +
                "ARRAY_SIZE=" + ARRAY_SIZE +
                ", measurements=" + this.showMeasurements(this.measurements) +
                '}';
    }
}
