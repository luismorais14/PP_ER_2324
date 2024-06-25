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

import com.estg.core.ContainerType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ContainerTypeImpl implements ContainerType {
    private final int ARRAY_SIZE = 30;

    private String[] types = new String[ARRAY_SIZE];

    /**
     * Método responsável por atualizar os tipos de containers
     */
    public void setContainerTypes() {
        JSONArray ja;
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("JSONFiles\\Types.json");
            ja = (JSONArray) parser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) ja.get(0);
            JSONArray typesArray = (JSONArray) jsonObject.get("types");
            this.types = new String[Math.min(typesArray.size(), ARRAY_SIZE)];
            for (int i = 0; i < typesArray.size() && i < ARRAY_SIZE; i++) {
                this.types[i] = (String) typesArray.get(i);
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

    /**
     * Método responsável por retornar a coleção de tipos de containers
     * @return a coleção de tipos
     */
    public String[] getTypes() {
        return this.types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContainerTypeImpl other = (ContainerTypeImpl) o;
        return Arrays.deepEquals(types, other.types);
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < this.types.length; i++) {
            if (this.types[i] != null && !this.types[i].isBlank()) {
                txt += this.types[i] + "\n";
            }
        }
        return txt;
    }
}
