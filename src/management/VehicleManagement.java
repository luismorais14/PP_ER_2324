package management;

import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;

public class VehicleManagement {
    private final int ARRAY_SIZE = 10;

    private Vehicle[] vehicles;
    private Vehicle[] enabledVehicles;
    private Vehicle[] disabledVehicles;
    private static int nEnabledVehicles = 0;
    private static int nDisabledVehicles = 0;
    private static int nVehicles = 0;

    /**
     * Construtor de VehicleManagement
     */
    public VehicleManagement() {
        this.vehicles = new Vehicle[ARRAY_SIZE];
        this.enabledVehicles = new Vehicle[ARRAY_SIZE];
        this.disabledVehicles = new Vehicle[ARRAY_SIZE];
    }

    /**
     * Método responsável por especificar a coleção de veículos
     *
     * @param vehicles a coleção de veículos
     */
    public void setEnabledVehiclesVehicles(Vehicle[] vehicles) {
        this.enabledVehicles = vehicles;
    }

    /**
     * Método responsável por especificar a coleção de veículos desativados
     * @param vehicles a coleção de veículos desativados
     */
    public void setDisabledVehicles(Vehicle[] vehicles) {
        this.disabledVehicles = vehicles;
    }

    /**
     * Método responsável por especificar a coleção de veículos
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
        return nEnabledVehicles;
    }

    /**
     * Método responsável por retornar o array de veículos
     * @return a coleção de veículos
     */
    public Vehicle[] getVehicles() {
        return this.vehicles;
    }

    /**
     * Método responsável por retornar a coleção de veículos ativos
     *
     * @return a coleção de veículos ativos
     */
    public Vehicle[] getEnabledVehiclesVehicles() {
        return this.enabledVehicles;
    }

    /**
     * Método responsável por retornar a coleção de veículos desativados
     * @return a coleção de veículos desativados
     */
    public Vehicle[] getDisabledVehiclesVehicles() {
        return this.disabledVehicles;
    }

    /**
     * Método responsável por adicionar um veículo à coleção de veículos  ativos
     * @param vehicle o veículo a ser adicionado
     * @return o sucesso ou insucesso da operação
     * @throws VehicleException exceção a ser lançada caso o veículo recebido como parâmetro seja null
     */
    public boolean addVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Null vehicle");
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
     * Método responsável por habilitar um veículo
     * @param vehicle veículo a ser habilitado
     * @throws VehicleException Exceção a ser lançada caso o veículo seja null ou já esteja habiltiado
     */
    public void enableVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Null vehicle");
        }
        if (verifyActiveVehicle(vehicle)) {
            throw new VehicleException("Vehicle is already enabled");
        }
        if (!verifyVehicleExistence(vehicle)) {
            throw new VehicleException("Vehicle does not exist");
        }
        if (nEnabledVehicles == this.enabledVehicles.length - 1) {
            expandActiveVehicleArray();
        } else {
            this.enabledVehicles[nEnabledVehicles] = vehicle;
            nEnabledVehicles++;
        }
        if (verifyDisableVehicle(vehicle)) {
            int index = findDisabledVehicle(vehicle);
            if (index == nDisabledVehicles) {
                this.disabledVehicles[index] = null;
            } else {
                for (int i = index; i < nDisabledVehicles - index - 1; i++) {
                    this.disabledVehicles[i] = this.disabledVehicles[i + 1];
                }
            }
            nDisabledVehicles--;
        }
    }

    /**
     * Método responsável por desabiltiar um veículo
     * @param vehicle veículo a ser desabilitado
     * @throws VehicleException Exceção a ser lançada caso o veículo seja null ou já esteja desabiltiado
     */
    public void disableVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Null vehicle");
        }
        if (verifyDisableVehicle(vehicle)) {
            throw new VehicleException("Vehicle is already disabled");
        }
        if (!verifyVehicleExistence(vehicle)) {
            throw new VehicleException("Vehicle does not exist");
        }
        if (nDisabledVehicles == this.disabledVehicles.length - 1) {
            expandDisabledVehicleArray();
        } else {
            this.disabledVehicles[nDisabledVehicles] = vehicle;
            nDisabledVehicles++;
        }
        if (verifyActiveVehicle(vehicle)) {
            int index = findEnabledVehicle(vehicle);
            if (index == nEnabledVehicles) {
                this.enabledVehicles[index] = null;
            } else {
                for (int i = index; i < nEnabledVehicles - index - 1; i++) {
                    this.enabledVehicles[i] = this.enabledVehicles[i + 1];
                }
            }
            nEnabledVehicles--;
        }
    }

    /**
     * Expande a capacidade do array de veículos.
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
     * Método responsável por verificar se o veículo se encontra ativo
     * @param vehicle o veículo a ser verificado
     * @return o sucesso ou insucesso da operação
     * @throws VehicleException exceção a ser lançada caso o veículo recebido como parâmetro seja null
     */
    private boolean verifyActiveVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Null vehicle");
        }
        for (int i = 0; i < nEnabledVehicles; i++) {
            if (this.enabledVehicles[i].equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por verificar se o veículo se encontra desativado
     * @param vehicle o veículo a ser verificado
     * @return o sucesso ou insucesso da operação
     * @throws VehicleException exceção a ser lançada caso o veículo recebido como parâmetro seja null
     */
    private boolean verifyDisableVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Null vehicle");
        }
        for (int i = 0; i < nDisabledVehicles; i++) {
            if (this.disabledVehicles[i].equals(vehicle)) {
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

    /**
     * Método responsável por encontrar a posição do veículo recebido como parâmetro na coleção de veículos ativos
     * @param vehicle o veículo a ser encontrado
     * @return a posição do veículo
     */
    private int findEnabledVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return -1;
        }
        for (int i = 0; i < nEnabledVehicles; i++) {
            if (this.enabledVehicles[i].equals(vehicle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método responsável por encontrar a posição do veículo recebido como parâmetro na coleção de veículos desativados
     * @param vehicle o veículo a ser encontrado
     * @return a posição do veículo
     */
    private int findDisabledVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return -1;
        }
        for (int i = 0; i < nDisabledVehicles; i++) {
            if (this.disabledVehicles[i].equals(vehicle)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Expande a capacidade do array de veículos habilitados.
     */
    private void expandActiveVehicleArray() {
        Vehicle[] newArray = new Vehicle[ARRAY_SIZE + 5];
        for (int i = 0; i < nEnabledVehicles; i++) {
            newArray[i] = this.enabledVehicles[i];
        }
        this.enabledVehicles = newArray;
    }

    /**
     * Expande a capacidade do array de veículos desabilitados.
     */
    private void expandDisabledVehicleArray() {
        Vehicle[] newArray = new Vehicle[ARRAY_SIZE + 5];
        for (int i = 0; i < nDisabledVehicles; i++) {
            newArray[i] = this.disabledVehicles[i];
        }
        this.disabledVehicles = newArray;
    }

    /**
     * Método responsável por mostrar em String o array de veículos
     * @return a string do array de veículos
     */
    private String showAllVehicles() {
        String txt = "";
        for (int i = 0; i < this.vehicles.length; i++) {
            if (this.vehicles[i] != null) {
                txt += this.vehicles[i].toString() + "\n";
            }
        }
        return txt;
    }

    /**
     * Método responsável por mostrar em String o array de veículos ativos
     * @return a string do array de veículos ativos
     */
    private String showEnabledVehicles() {
        String txt = "";
        for (int i = 0; i < this.enabledVehicles.length; i++) {
            if (this.enabledVehicles[i] != null) {
                txt += this.enabledVehicles[i].toString() + "\n";
            }
        }
        return txt;
    }

    /**
     * Método responsável por mostrar em String o array de veículos inativos
     * @return a string do array de veículos inativos
     */
    private String showDisabledVehicles() {
        String txt = "";
        for (int i = 0; i < this.disabledVehicles.length; i++) {
            if (this.disabledVehicles[i] != null) {
                txt += this.disabledVehicles[i].toString() + "\n";
            }
        }
        return txt;
    }

    @Override
    public String toString() {
        return "All Vehicles: " + this.showAllVehicles() +
                "\nEnabled vehicles: " + this.showEnabledVehicles() +
                "\nDisabled Vehicles: " + this.showDisabledVehicles();
    }
}
