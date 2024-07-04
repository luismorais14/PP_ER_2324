/*
 * Nome: Francisco Morais de Oliveira
 * Número: 8230204
 * Turma: T3
 *
 * Nome: Luís André Nunes Morais
 * Número: 8230258
 * Turma: T3
 */
package io;

import alerts.AlertSystem;
import com.estg.io.HTTPProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataHandler {

    AlertSystem alertSystem;

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToAidBoxes() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if(fdir.mkdirs()){
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
        }
        try {
            fw = new FileWriter("JSONFiles\\AidBoxes.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.alertSystem = new AlertSystem(ex, ex.getMessage());
            this.alertSystem.logCreater();
            return;
        }
        fw.write(str);
        fw.close();
    }

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToContainers() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/containers");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if (fdir.mkdirs()) {
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
            try {
                fw = new FileWriter("JSONFiles\\Containers.json");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                this.alertSystem = new AlertSystem(ex, ex.getMessage());
                this.alertSystem.logCreater();
                return;
            }
            fw.write(str);
            fw.close();
        }
    }

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToDistances() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if(fdir.mkdirs()){
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
        }
        try {
            fw = new FileWriter("JSONFiles\\Distances.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.alertSystem = new AlertSystem(ex, ex.getMessage());
            this.alertSystem.logCreater();
            return;
        }
        fw.write(str);
        fw.close();
    }

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToTypes() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/types");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if(fdir.mkdirs()){
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
        }
        try {
            fw = new FileWriter("JSONFiles\\Types.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.alertSystem = new AlertSystem(ex, ex.getMessage());
            this.alertSystem.logCreater();
            return;
        }
        fw.write(str);
        fw.close();
    }

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToVehicles() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/vehicles");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if(fdir.mkdirs()){
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
        }

        try {
            fw = new FileWriter("JSONFiles\\Vehicles.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.alertSystem = new AlertSystem(ex, ex.getMessage());
            this.alertSystem.logCreater();
            return;
        }
        fw.write(str);
        fw.close();
    }

    /**
     * Método responsável por guardar os dados da WEBAPI em ficheiro .json
     * @throws IOException exceção a ser lançada caso mão seja possível encontrar o ficheiro
     */
    public void apiToReadings() throws IOException {
        FileWriter fw;
        HTTPProvider http = new HTTPProvider();
        String str = http.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/readings");

        File fdir = new File("JSONFiles");
        if (!fdir.exists()) {
            if(fdir.mkdirs()){
                System.out.println("Folder created");
            } else {
                System.out.println("Folder creation failed");
                this.alertSystem = new AlertSystem(fdir.mkdirs(), "Folder creation failed.");
                this.alertSystem.logCreater();
            }
        }

        try {
            fw = new FileWriter("JSONFiles\\Readings.json");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            this.alertSystem = new AlertSystem(ex, ex.getMessage());
            this.alertSystem.logCreater();
            return;
        }
        fw.write(str);
        fw.close();
    }
}
