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
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import io.ImporterImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import management.PickingMapImpl;
import management.ReportImpl;
import management.RouteGeneratorImpl;
import management.RouteImpl;
import management.VehicleImpl;

public class Menus {

    ImporterImpl importer = new ImporterImpl();
    InstitutionImpl institution;
    AidBoxImpl aidbox;
    ContainerImpl container;
    VehicleImpl vehicle;
    Route route;                                            // Ver se estão certas estas variaveis 
    PickingMapImpl pm;
    RouteGenerator rg;
    ReportImpl rp;

    /**
     * Método construtor de Menus
     * @param instn a instituição do programa
     */
    public Menus(InstitutionImpl instn) {
        this.institution = instn;
        this.aidbox = new AidBoxImpl();
        this.container = new ContainerImpl();
        this.vehicle = new VehicleImpl();
        this.rp = new ReportImpl();
        this.pm = new PickingMapImpl();
        this.rg = new RouteGeneratorImpl();
        this.route = new RouteImpl();

    }

    /**
     * Método responsável por exibir o menu principal e as suas funcionalidades
     * @throws AidBoxException exceção a ser lançada caso alguma aidbox da instituição seja null
     * @throws ContainerException exceção a ser lançada caso algum container da instituição seja null
     * @throws VehicleException exceção a ser lançada caso algum veículo da instituição seja null
     */
    public void MainMenu() throws AidBoxException, ContainerException, VehicleException{
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String aidcode = "";
        String lixo = "";
        boolean aidBoxFound = false;
        while (!aux || inputNum != 5) {
            System.out.println("================================");
            System.out.println("|       MENU SELECTION         |");
            System.out.println("================================");
            System.out.println("| Options:                     |");
            System.out.println("|        1. Manage AidBoxes    |");
            System.out.println("|        2. Manage Vehicles    |");
            System.out.println("|        3. Import Data        |");
            System.out.println("|        4. Generate Routes    |");
            System.out.println("|        5. Exit               |");
            System.out.println("================================");
            System.out.println("Enter your option: ");
            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (InputMismatchException ex) {
                System.out.println("Opção Inválida");
                lixo = input.nextLine(); //limpar o buffer
            }
            switch (inputNum) {
                case 1:
                    this.ManageAidboxesMenu();
                    break;
                case 2:
                    this.ManageVehiclesMenu();
                    break;
                case 3:
                    this.ImportDataMenu();
                    break;
                case 4:
                    this.generateRoutesMenu(this.institution);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 5.");
                    break;

            }
        }
    }

    /**
     * Método responsável por exibir o menu de importação de dados e gerência a
     * importação de dados para a instituição
     *
     * @throws AidBoxException    se ocorrer um erro em relação aos aidboxes.
     * @throws ContainerException se ocorrer um erro em relaçao aos containers.
     */
    private void ImportDataMenu() {
        try {
            this.importer.importData(this.institution);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InstitutionException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Data Imported Successfully!");
    }

    /**
     * Método responsável por exibir o menu de gerir aidboxes
     *
     * @throws AidBoxException exceção a ser lançada caso ocorra um erro relacionado aos aidboxes.
     */
    private void ManageAidboxesMenu() throws AidBoxException, ContainerException {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String aidcode = "";
        int code = 0;
        String zone = "";
        String lixo = "";
        boolean aidBoxFound = false;
        while (!aux || inputNum != 4) {
            System.out.println("+===================================+");
            System.out.println("|       MANAGE AIDBOXES            |");
            System.out.println("+===================================+");
            System.out.println("| Options:                         |");
            System.out.println("|   1. Add Aidbox                  |");
            System.out.println("|   2. Add Container               |");
            System.out.println("|   3. List Containers             |");
            System.out.println("|   4. Exit                        |");
            System.out.println("+===================================+");
            System.out.print("Enter your option: ");

            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (InputMismatchException ex) {
                System.out.println("Opção Inválida");
                lixo = input.nextLine(); //limpa o buffer
            }
            switch (inputNum) {
                case 1:
                    boolean codeExists = true;
                    while (codeExists) {
                        System.out.println("Enter the AidBox Code: ");
                        try {
                            code = input.nextInt();
                            aidcode = "CAIXF" + code;
                            codeExists = false;
                            for (int i = 0; i < institution.getAidBoxes().length; i++) {
                                if (institution.getAidBoxes()[i] != null && institution.getAidBoxes()[i].getCode().compareTo(aidcode) == 0) {
                                    System.out.println("Code already exists. Please enter a different code.");
                                    codeExists = true;
                                    break;
                                }
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid character.");
                            lixo = input.nextLine(); //clear buffer
                            codeExists = true;
                        }
                    }

                    System.out.println("Enter the AidBox Zone: ");
                    try {
                        zone = input.next();
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid character.");
                        lixo = input.nextLine(); //clear buffer
                    }
                    aidbox = new AidBoxImpl(aidcode, zone);
                    institution.addAidBox(this.aidbox);
                    break;
                case 2:
                    boolean aidboxExists = true;
                    int typeChoice = -1;
                    ContainerType ct;
                    int containerCapacity = 0;
                    String containerCode = "";
                    boolean codeValid = false;

                    while (aidboxExists) {
                        System.out.println("Enter the AidBox Code: ");
                        try {
                            String aidboxCode = input.next();
                            aidboxExists = false;
                            for (int i = 0; i < institution.getAidBoxes().length; i++) {
                                if (institution.getAidBoxes()[i] != null && institution.getAidBoxes()[i].getCode().compareTo(aidboxCode) == 0) {
                                    System.out.println("Select one of the above container types: ");
                                    for (int j = 0; j < TypesManagement.getTypes().length; j++) {
                                        System.out.println("" + j + ":\t" + TypesManagement.getTypes()[j]);
                                    }
                                    while (typeChoice < 0 || typeChoice >= TypesManagement.getTypes().length) {
                                        try {
                                            typeChoice = input.nextInt();
                                        } catch (InputMismatchException ex) {
                                            System.out.println("Invalid character.");
                                            input.nextLine(); //clear buffer
                                        }
                                    }
                                    ct = TypesManagement.getContainerTypes()[typeChoice];

                                    System.out.println("Enter the containers Capacity: ");
                                    try {
                                        containerCapacity = input.nextInt();
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Invalid number.");
                                        input.nextLine(); //clear buffer
                                    }

                                    System.out.println("Enter the container Code: ");
                                    try {
                                        containerCode = input.next();
                                        codeValid = false;
                                        for (int j = 0; j < institution.getAidBoxes().length; j++) {
                                            for (int k = 0; k < institution.getAidBoxes()[j].getContainers().length; k++) {
                                                if (institution.getAidBoxes()[j].getContainers()[k] != null && institution.getAidBoxes()[j].getContainers()[k].getCode().compareTo(containerCode) == 0) {
                                                    System.out.println("Code already exists. Please enter a different code.");
                                                    codeValid = true;
                                                    break;
                                                }
                                            }
                                            if (codeValid) {
                                                break;
                                            }
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Invalid character.");
                                        input.nextLine(); //clear buffer
                                        codeValid = true;
                                    }

                                    if (!codeValid) {
                                        ContainerImpl container = new ContainerImpl(ct, containerCapacity, containerCode);
                                        for (int j = 0; j < institution.getAidBoxes().length; j++) {
                                            if (institution.getAidBoxes()[i] != null && institution.getAidBoxes()[i].getCode().compareTo(aidboxCode) == 0) {
                                                institution.getAidBoxes()[i].addContainer(container);
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid character.");
                            input.nextLine(); //clear buffer
                            aidboxExists = true;
                        }
                    }
                    break;
                case 3:
                    int counter = 0;

                    for (int i = 0; i < this.institution.getAidBoxes().length; i++) {
                        for (int j = 0; j < this.institution.getAidBoxes()[i].getContainers().length; j++) {
                            System.out.println("" + counter + "- " + this.institution.getAidBoxes()[i].getContainers()[j].toString());
                            counter++;
                            System.out.println("\n");
                        }
                        System.out.println("\n");
                    }

                case 4:
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    break;
            }
        }

    }


    /**
     * Método responsável por exibir o menu de veículos
     * @throws VehicleException exceção a ser lançada caso ocorra um erro com os veículos
     */
    private void ManageVehiclesMenu() throws VehicleException {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        double capacity = 0.0;
        String lixo = "";
        boolean aidBoxFound = false;
        TypesManagement type = new TypesManagement();
        char vType = ' ';

        while (!aux || inputNum != 3) {
            System.out.println("+===================================+");
            System.out.println("|       MANAGE VEHICLES           |");
            System.out.println("+===================================+");
            System.out.println("| Options:                         |");
            System.out.println("|   1. Enable Vehicle              |");
            System.out.println("|   2. Disable Vehicle             |");
            System.out.println("|   3. Exit                        |");
            System.out.println("+===================================+");
            System.out.print("Enter your option: ");
            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (InputMismatchException ex) {
                System.out.println("Opção Inválida");
                lixo = input.nextLine(); //limpar o buffer
            }
            switch (inputNum) {
                case 1:
                    int counter = 0;
                    int option = -1;
                    System.out.println("Select the vehicle to enable: ");
                    for (int i = 0; i < institution.getVehicles().length; i++) {
                        System.out.println("" + counter + "-\t" + this.institution.getVehicles()[i].toString());
                        counter++;
                    }
                    try {
                        option = input.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid character.");
                        input.nextLine(); //clear buffer
                    }
                    institution.enableVehicle(institution.getVehicles()[option]);
                    System.out.println("Vehicle successfully enabled!");
                    break;
                case 2:
                    counter = 0;
                    option = -1;
                    System.out.println("Select the vehicle to enable: ");
                    for (int i = 0; i < institution.getVehicles().length; i++) {
                        System.out.println("" + counter + "-\t" + this.institution.getVehicles()[i].toString());
                        counter++;
                    }
                    try {
                        option = input.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid character.");
                        input.nextLine(); //clear buffer
                    }
                    institution.disableVehicle(institution.getVehicles()[option]);
                    System.out.println("Vehicle successfully Disabled!");
                    break;
                case 3:
                    aux = true;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                    break;
            }
        }

    }

    /**
     * Método responsável por chamar a função que gere as rotas
     * @param instn instituição a gerar rotas
     */
    private void generateRoutesMenu(Institution instn) {
        this.rg.generateRoutes(instn);
    }


}

