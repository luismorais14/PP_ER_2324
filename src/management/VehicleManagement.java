package management;

import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;

public class VehicleManagement {
    private final int ARRAY_SIZE = 10;

    private Vehicle[] vehicles;
    private static int nVehicles = 0;

    /**
     * Construtor de VehicleManagement
     */
    public VehicleManagement() {
        vehicles = new Vehicle[ARRAY_SIZE];
    }

    /**
     * Método responsável por especificar a coleção de veículos
     *
     * @param vehicles a coleção de veículos
     */
    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Método responsável por retornar o número de veículos
     *
     * @return o número de veículos
     */
    public int getNVehicles() {
        return nVehicles;
    }

    /**
     * Método responsável por retornar a coleção de veículos
     *
     * @return a coleção de veículos
     */
    public Vehicle[] getVehicles() {
        return this.vehicles;
    }

    public boolean addVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            return false;
        }
        if (nVehicles == this.vehicles.length - 1) {
            expandVehicleArray();
        }
        if (this.verifyVehicleExistence(vehicle)) {
            return false;
        } else {
            this.vehicles[nVehicles] = vehicle;
            nVehicles++;
        }
        return true;
    }

    /**
     * Método responsável por remover um veículo da coleção de veículos
     * @param vehicle o veículo a ser removido
     * @throws VehicleException exceção a ser lançada caso o veículo recebido como parâmetro seja null
     */
    public void removeVehicle(Vehicle vehicle) throws VehicleException {
        int index = this.findVehicle(vehicle);
        if (vehicle == null) {
            throw new VehicleException("Vehicle does not exist");
        }
        if (index == nVehicles) {
            this.vehicles[index] = null;
        } else {
            for (int i = index; i < nVehicles - index - 1; i++) {
                this.vehicles[i] = this.vehicles[i + 1];
            }
            nVehicles--;
        }
    }

    /**
     * Expande a capacidade do array de veículos habilitados.
     */
    private void expandVehicleArray() {
        Vehicle[] newArray = new Vehicle[ARRAY_SIZE + 5];
        for (int i = 0; i < nVehicles; i++) {
            newArray[i] = this.vehicles[i];
        }
        this.vehicles = newArray;
    }

    /**
     * Método responsáve por verificar a existência de um veículo recebido como parâmetro na coleção de veículos
     *
     * @param vehicle o veículo a ser verificado
     * @return o sucesso ou insucesso da operação
     * @throws VehicleException exceção a ser lançada caso o veículo recebido como parâmetro seja null
     */
    private boolean verifyVehicleExistence(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Vehicle is null.");
        }
        for (int i = 0; i < nVehicles; i++) {
            if (this.vehicles[i].equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por encontrar a posição do veículo recebido como parâmetro na coleção de veículos
     * @param vehicle o veículo a ser encontrado
     * @return a posição do veículo
     */
    private int findVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return -1;
        }
        for (int i = 0; i < nVehicles; i++) {
            if (this.vehicles[i].equals(vehicle)) {
                return i;
            }
        }
        return -1;
    }

}
