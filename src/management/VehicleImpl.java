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

    /**
     * Construtor de VehicleImpl
     */
    public VehicleImpl() {
        this.code = "";
    }

    /**
     * Construtor de VehicleImpl
     *
     * @param code o código do veículo
     */
    public VehicleImpl(String code) {
        this.code = code;
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

    @Override
    public double getCapacity(ContainerType ct) {
        if (ct == null) {
            return 0.0;
        }

        double clothingCapacity = 0.0;
        double medicideCapacity = 0.0;
        double perishableCapacity = 0.0;
        double nonPerishableCapacity = 0.0;

        if (ct instanceof ContainerTypeImpl) {
            ContainerTypeImpl containerType = (ContainerTypeImpl) ct;
            String[] types = containerType.getTypes();


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
                            clothingCapacity = ((Number) clothing).doubleValue();
                        }
                        if (medicine instanceof Number) {
                            medicideCapacity = ((Number) medicine).doubleValue();
                        }
                        if (perishable instanceof Number) {
                            perishableCapacity = ((Number) perishable).doubleValue();
                        }
                        if (nonPerishable instanceof Number) {
                            nonPerishableCapacity = ((Number) nonPerishable).doubleValue();
                        }

                    }


                        switch (containerType) {
                            case "clothing":
                                return clothingCapacity;
                            case "medicine":
                                return medicideCapacity;
                            case "perishable food":
                                return perishableCapacity;
                            case "non perishable food":
                                return nonPerishableCapacity;
                            default:
                                return 0.0;
                        }

                }
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
                return 0.0;
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                return 0.0;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return 0.0;
            }
        }
        return 0.0;
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
