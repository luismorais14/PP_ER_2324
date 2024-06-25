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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import core.ContainerTypeImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VehicleImpl implements Vehicle {

    private String code;
    private double clothingCapacity;
    private double medicineCapacity;
    private double perishableCapacity;
    private double nonPerishableCapacity;

    /**
     * Construtor de VehicleImpl
     */
    public VehicleImpl() {
        this.code = "";
        this.clothingCapacity = 0.0;
        this.medicineCapacity = 0.0;
        this.perishableCapacity = 0.0;
        this.nonPerishableCapacity = 0.0;
    }

    /**
     * Construtor de VehicleImpl
     * @param code o código do veículo
     * @param clothing o número de containers do tipo 'clothing' que o veículo pode transportar
     * @param medicine o número de containers do tipo 'medicine' que o veículo pode transportar
     * @param perishable o número de containers do tipo 'perishable food' que o veículo pode transportar
     * @param nonPerishable o número de containers do tipo 'non perishable food que o veículo pode transportar
     */
    public VehicleImpl(String code, double clothing, double medicine, double perishable, double nonPerishable) {
        this.code = code;
        this.clothingCapacity = clothing;
        this.medicineCapacity = medicine;
        this.perishableCapacity = perishable;
        this.nonPerishableCapacity = nonPerishable;
    }

    /**
     * Método responsável por especificar o código do veículo
     *
     * @param code o código do veículo
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Método responsável por retornar o código do veículo
     *
     * @return o código do veículo
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Método responsável por especificar as capacidades para cada tipo de container
     */
    public void setCapacities() {
        JSONArray ja;
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("JSONFiles\\Vehicles.json");
            ja = (JSONArray) parser.parse(fileReader);
            for (Object obj : ja) {
                JSONObject jsonObject = (JSONObject) obj;
                String vehicleCode = (String) jsonObject.get("code");
                if (vehicleCode.compareTo(this.code) == 0) {
                    JSONObject capacites = (JSONObject) jsonObject.get("capacity");
                    Object clothing = capacites.get("clothing");
                    Object medicine = capacites.get("medicine");
                    Object perishable = capacites.get("perishable food");
                    Object nonPerishable = capacites.get("non perishable food");

                    if (clothing instanceof Number) {
                        this.clothingCapacity = ((Number) clothing).doubleValue();
                    }
                    if (medicine instanceof Number) {
                        this.medicineCapacity = ((Number) medicine).doubleValue();
                    }
                    if (perishable instanceof Number) {
                        this.perishableCapacity = ((Number) perishable).doubleValue();
                    }
                    if (nonPerishable instanceof Number) {
                        this.nonPerishableCapacity = ((Number) nonPerishable).doubleValue();
                    }

                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    @Override
    public double getCapacity(ContainerType ct) {
        if (ct == null || !(ct instanceof ContainerTypeImpl)) {
            return 0.0;
        }


    }

    /**
     * Método responsável por criar um deep copy de Vehicle
     *
     * @return uma deep copy de Vehicle
     */
    public Object deepCopyVehicle() {
        VehicleImpl clone = new VehicleImpl();

        clone.setCode(this.getCode());
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleImpl vehicle = (VehicleImpl) o;
        return this.code.compareTo(vehicle.getCode()) == 0;
    }

    @Override
    public String toString() {
        return "Code: " + this.code
                + "\nCapacity: " + this.capacity;
    }
}
