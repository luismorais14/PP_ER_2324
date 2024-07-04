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

import alerts.AlertSystem;
import com.estg.core.ContainerType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TypesManagement {

    private static final int ARRAY_SIZE = 10;

    private static String[] types = new String[ARRAY_SIZE];
    private static ContainerType[] containerTypes = new ContainerType[ARRAY_SIZE];
    private static AlertSystem alertSystem = new AlertSystem();

    /**
     * Método responsável por retornar a coleção de tipos em String
     * @return a coleção de tipos em String
     */
    public static String[] getTypes() {
        return types;
    }

    /**
     * Método responsável por retornar a coleção de tipos
     * @return a coleção de tipos
     */
    public static ContainerType[] getContainerTypes() {
        return containerTypes;
    }

    /**
     * Método responsável por atualizar os tipos de containers
     */
    public static void setContainerTypes() {
        JSONArray ja;
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("JSONFiles\\Types.json");
            ja = (JSONArray) parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) ja.get(0);
            JSONArray typesArray = (JSONArray) jsonObject.get("types");
            types = new String[Math.min(typesArray.size(), ARRAY_SIZE)];
            for (int i = 0; i < typesArray.size() && i < ARRAY_SIZE; i++) {
                types[i] = (String) typesArray.get(i);
                containerTypes[i] = new ContainerTypeImpl((String) typesArray.get(i));
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            alertSystem = new AlertSystem(ex, ex.getMessage());
            alertSystem.logCreater();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            alertSystem = new AlertSystem(ex, ex.getMessage());
            alertSystem.logCreater();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            alertSystem = new AlertSystem(ex, ex.getMessage());
            alertSystem.logCreater();
        }
    }

    /**
     * Método responsável por retornar um containerType dado uma string
     * @param type a string
     * @return o containerType
     */
    public static ContainerType findByType(String type) {
        for (int i = 0; i < types.length; i++) {
            if (type.compareTo(types[i]) == 0) {
                return containerTypes[i];
            }
        }
        return null;
    }
}
