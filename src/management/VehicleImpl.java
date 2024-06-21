/*
* Nome: Francisco Morais de Oliveira
* Número: 8230204
* Turma: T3
*
* Nome: Luís André Nunes Morais
* Número: 8230258
* Turma: T3
 */
package management;

import com.estg.core.ContainerType;
import com.estg.pickingManagement.Vehicle;


public class VehicleImpl implements Vehicle {

    private double capacity;
    private String code;

    /**
     * Construtor de VehicleImpl
     */
    public VehicleImpl() {
        this.capacity = 0.0;
        this.code = "";
    }

    /**
     * Construtor de VehicleImpl
     * @param capacity a capacidade do veículo
     * @param code o código do veículo
     */
    public VehicleImpl(double capacity, String code) {
        this.capacity = capacity;
        this.code = code;
    }

    /**
     * Método responsável por especificar a capacidade do veículo
     * @param capacity a capacidade do veíuculo
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Método responsável por especificar o código do veículo
     * @param code o código do veículo
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Método responsável por retornar o código do veículo
     * @return
     */
    @Override
    public String getCode() {
        return this.code;
    }

    //talvez ir buscar à API a capacidade, idk
    @Override
    public double getCapacity(ContainerType ct) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}