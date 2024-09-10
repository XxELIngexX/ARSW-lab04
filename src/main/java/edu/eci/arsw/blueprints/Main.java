package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        BlueprintsServices blueprintsServices = new BlueprintsServices();
        Point[] points = new Point[]{new Point(15, 25), new Point(23, 15)};
        Point[] points1 = new Point[]{new Point(12, 16), new Point(14, 16)};
        Point[] points2 = new Point[]{new Point(16, 12), new Point(16, 14)};


        Blueprint blueprint = new Blueprint("cesar", "theBestPlan", points);
        Blueprint blueprint2 = new Blueprint("cesar", "aNicePlan", points2);
        Blueprint blueprint1 = new Blueprint("miguel", "notGoodplan", points1);

        //añadir los planos a memoria
        try {
            blueprintsServices.addNewBlueprint(blueprint);
            blueprintsServices.addNewBlueprint(blueprint1);
            blueprintsServices.addNewBlueprint(blueprint2);


        } catch (BlueprintPersistenceException e) {
            System.out.println("Error retrieving blueprint: " + e.getMessage());
        }


        // Consultar plano por autor y nombre
        try {
            Blueprint found = blueprintsServices.getBlueprint("cesar", "theBestPlan");
            System.out.println(" Blueprint found: " + found.getName() + " by " + found.getAuthor());
        } catch (Exception e) {
            System.out.println("Error don't found blueprint: " + e.getMessage());
        }

        //consultar todos los planos de un autor
        try {
            Set<Blueprint> founds = blueprintsServices.getBlueprintsByAuthor("cesar");
            System.out.println("The following BluePrints were found:");
            for (Blueprint i:founds){
                System.out.println(i.getName());
            }
        }catch (Exception e){
            System.out.println("Error don't found blueprint by this author: " + e.getMessage());

        }

        //consultar todos los blueprints
        try {
            Set<Blueprint> founds = blueprintsServices.getAllBlueprints();
            System.out.println("The following BluePrints were found:");
            for (Blueprint i:founds){
                System.out.println(i.getName() + " by " + i.getAuthor());
            }
        }catch (Exception e){
            System.out.println("Error don't found any blueprint " + e.getMessage());

        }

    }
}
