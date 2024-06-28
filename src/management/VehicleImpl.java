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
import java.util.Map;
import java.util.Set;

import core.ContainerTypeImpl;
import core.TypesManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VehicleImpl implements Vehicle {

    private final int ARRAY_SIZE = 10;

    private String code;
    private double[] capacities = new double[ARRAY_SIZE];

    /**
     * Construtor de VehicleImpl
     */
    public VehicleImpl() {
        this.code = "";
    }

    /**
     * Cosntrutor de VehicleImpl
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

    /**
     * Método responsável por especificar as capacidades para cada tipo de container
     */
    public void setCapacities() {
        JSONArray ja;
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("JSONFiles\\Vehicles.json");
            ja = (JSONArray) parser.parse(fileReader);
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jsonObject = (JSONObject) ja.get(i);
                String vehicleCode = (String) jsonObject.get("code");
                if (vehicleCode.compareTo(this.code) == 0) {
                    JSONObject capacities = (JSONObject) jsonObject.get("capacity");
                    for (int j = 0; j < capacities.size(); j++) {
                        String type = TypesManagement.getTypes()[j];
                        if (capacities.get(type) != null) {
                            this.capacities[j] = ((Number) capacities.get(type)).doubleValue();
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método responsável por retornar a capacidade do veíclo em função do tipo recebido como parâmetro
     * @param ct o tipo de container
     * @return a capacidade do veículo para o tipo recebido como parâmetro
     */
    @Override
    public double getCapacity(ContainerType ct) {
        if (!(ct instanceof ContainerTypeImpl)) {
            return 0.0;
        }

        ContainerTypeImpl cti = (ContainerTypeImpl) ct;

        for (int i = 0; i < TypesManagement.getContainerTypes().length; i++) {
            if (cti.equals(TypesManagement.getContainerTypes()[i])) {
                return this.capacities[i];
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

    /**
     * Método responsável por mostrar em String o array de capacidades
     * @return a string do array de capacidades
     */
    private String showCapacities() {
        String txt = "";
        for (int i = 0; i < this.capacities.length; i++) {
            txt += TypesManagement.getTypes()[i] + ": " + this.capacities[i] + "\n";
        }
        return txt;
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
                + "\nCapacity: {"
                + this.showCapacities()
                + "\n}";

    }
}
