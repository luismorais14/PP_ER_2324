/*
* Nome: Francisco Morais de Oliveira
* Número: 8230204
* Turma: T3
*
* Nome: Luís André Nunes Morais
* Número: 8230258
* Turma: T3
*/
package project;

import com.estg.core.exceptions.AidBoxException;

public class PP_ER_2324 {

    public static void main(String[] args) throws AidBoxException {
        AidBoxImpl aidbox1 = new AidBoxImpl("CAIXF51", "Lousada");
        AidBoxImpl aidbox2 = new AidBoxImpl("CAIXF38", "Felgueiras");

        double distance = 0.0;
        double duration = 0.0;

        distance = aidbox1.getDistance(aidbox2);
        duration = aidbox1.getDuration(aidbox2);

        System.out.println(distance);
        System.out.println(duration);

    }
    
}
