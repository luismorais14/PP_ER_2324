package alerts;

import java.io.*;
import java.time.LocalDateTime;

public class AlertSystem {
    private LocalDateTime time;
    private Object invalidObject;
    private String description;

    /**
     * Construtor de AlertSystem
     */
    public AlertSystem() {
        this.time = LocalDateTime.now();
        this.invalidObject = null;
        this.description = "";
    }

    /**
     * Construtor de AlertSystem
     *
     * @param invalidObject o objeto inválido
     */
    public AlertSystem(Object invalidObject, String description) {
        this.time = LocalDateTime.now();
        this.invalidObject = invalidObject;
        this.description = description;
    }

    /**
     * Método responsável por retornar a data do ocorrido
     *
     * @return a data do ocorrido
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Método responsável por retornar o objeto inválido
     *
     * @return o objeto inválido
     */
    public Object getInvalidObject() {
        return this.invalidObject;
    }

    /**
     * Método responsável por criar um ficheiro de texto com os alertas
     */
    public void logCreater() {
        String str = this.toString();
        try {
            File file = new File("Logger.txt");
            if (file.createNewFile()) {
                System.out.println("File created!");
            } else {
                System.out.println("File already exists!");
            }
            FileWriter fw = new FileWriter("Logger.txt", true);
            fw.write(str);
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    @Override
    public String toString() {
        return "Date: " + this.getTime()
                + "\nObject: " + this.getInvalidObject().toString()
                + "\nDescription: " + this.description
                +  "\n";
    }
}
