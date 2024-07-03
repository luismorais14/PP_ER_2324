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
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.Vehicle;
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

    public Menus(InstitutionImpl instn) { // Ver se esta correto
        this.institution = instn;
        this.aidbox = new AidBoxImpl();
        this.container = new ContainerImpl();
        this.vehicle = new VehicleImpl();
        this.rp = new ReportImpl();
        this.pm = new PickingMapImpl();
        this.rg = new RouteGeneratorImpl();
        this.route = new RouteImpl();

    }

    public void MainMenu() throws AidBoxException, ContainerException, VehicleException, PickingMapException {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String aidcode = "";
        String lixo = "";
        boolean aidBoxFound = false;
        while (!aux || inputNum != 4) {
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
                    //ManageAidboxesMenu();
                    break;
                case 2:
                    //ManageVehiclesMenu(); // Ver comentarios
                    break;
                case 3:
                    this.ImportDataMenu();
                    break;

                case 4:
                    //generateRoutesMenu(); // Fazer
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
         * @throws AidBoxException se ocorrer um erro em relação aos aidboxes.
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
         * Método responsável por exibir o menu de gerenciamento de aidboxes e
         * gerencia as operações relacionadas a aidboxes.
         *
         * @throws AidBoxException se ocorrer um erro relacionado aos aidboxes.
         * @throws ContainerException se ocorrer um erro relacionado aos containers.
         */
        public void ManageAidboxesMenu () throws AidBoxException, ContainerException {
            Scanner input = new Scanner(System.in);
            boolean aux = false;
            int inputNum = 0;
            String aidcode = "";
            String lixo = "";
            boolean aidBoxFound = false;
            while (!aux || inputNum != 4) {
                System.out.println("+===================================+");
                System.out.println("|       MANAGE AIDBOXES           |");
                System.out.println("+===================================+");
                System.out.println("| Options:                         |");
                System.out.println("|   1. Add Aidbox                  |");
                System.out.println("|   2. Add Container               |");
                System.out.println("|   3. List Containers             |");
                System.out.println("|   4. Exit                        |");
                System.out.println("+===================================+");
                System.out.print("Enter your option: ");
            }
            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (InputMismatchException ex) {
                System.out.println("Opção Inválida");
                lixo = input.nextLine(); //limpa o buffer
            }
            switch (inputNum) {
                case 1:
                    aidbox = new AidBoxImpl();
                    institution.addAidBox(this.aidbox);
                    break;
                case 2:
                    for (int i = 0; i < institution.getAidBoxes().length; i++) {
                        if (institution.getAidBoxes()[i] != null) {
                            System.out.println("Enter the aidbox code: ");
                            aidcode = input.next();
                            if (institution.getAidBoxes()[i].getCode().compareTo(aidcode) == 0) {
                                aidBoxFound = true;
                                ContainerImpl container = new ContainerImpl();
                                try {
                                    institution.getAidBoxes()[i].addContainer(container);
                                    System.out.println("Container added successfully.");
                                } catch (ContainerException e) {
                                    System.out.println("Failed to add container: " + e.getMessage());
                                }
                                break;
                            }
                        }
                    }

                    if (!aidBoxFound) {
                        System.out.println("Aidbox with the given code not found.");
                    }
                    break;
                case 3:
                    for (int i = 0; i < institution.getAidBoxes().length; i++) {
                        if (institution.getAidBoxes()[i] != null) {
                            if (institution.getAidBoxes()[i].getCode().compareTo(aidcode) == 0) {
                                aidBoxFound = true;
                                boolean hasContainers = false;
                                for (int j = 0; j < institution.getAidBoxes()[i].getContainers().length; j++) {
                                    if (institution.getAidBoxes()[i].getContainers()[j] != null) {
                                        System.out.println(institution.getAidBoxes()[i].getContainers()[j].toString());
                                        hasContainers = true;
                                    }
                                }
                                if (!hasContainers) {
                                    System.out.println("There are no containers in this aidbox.");
                                }
                                break;
                            }
                        }
                    }
                    if (!aidBoxFound) {
                        System.out.println("Aidbox with the given code not found.");
                    }
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                    break;
            }
            input.close();
        }


        public void ManageVehiclesMenu () throws VehicleException {
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
                System.out.println("|   1. Add Vehicle                 |");
                System.out.println("|   2. Create Vehicle              |");
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
                        vehicle = new VehicleImpl();
                        institution.addVehicle(vehicle);
                        break;
                    case 2:
                        System.out.println("Enter the vehicle capacity: (Kg)");
                        try {
                            capacity = input.nextDouble();
                        } catch (InputMismatchException ex) {
                            System.out.println("Invalid capacity value. Please enter a numeric value.");
                            lixo = input.nextLine(); // limpa o buffer
                            continue;
                        }
                        System.out.print("Enter the type of vehicle: ");
                        lixo = input.nextLine(); // limpa o buffer
                        String vehicleType = input.nextLine();

                        ContainerType containerType = TypesManagement.findByType(vehicleType);

                        if (containerType == null) {
                            System.out.println("Invalid vehicle type, please enter a valid type.");
                            continue;
                        }
                        //Vehicle createdVehicle = new VehicleImpl(capacity, containerType); // Corrigir construtores
                        //institution.addVehicle(createdVehicle);
                        //System.out.println("Vehicle created successfully!");
                        break;
                    case 3:
                        aux = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 3.");
                        break;
                }
                input.close();
            }

        }


    }

