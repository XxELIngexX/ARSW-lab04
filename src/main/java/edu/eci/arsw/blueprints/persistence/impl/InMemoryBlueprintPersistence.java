/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.*;



/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null){
            throw new  BlueprintNotFoundException("Not found BluePrint from this author or this Blueprint name");
        }else {
           return bp;
        }

    }



    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> authorBp = new HashSet<>();

        for(Map.Entry<Tuple<String, String>, Blueprint> tupleToSearch : blueprints.entrySet()){
            if (tupleToSearch.getValue().getAuthor().equals(author) ){
                authorBp.add(tupleToSearch.getValue());
            }

        }
        if (authorBp.isEmpty()){
            throw new  BlueprintNotFoundException("Not found BluePrint from this author.");
        }else{
            return authorBp;

        }


    }

    public Set<Blueprint> getAllBlueprint() throws BlueprintNotFoundException{
        Set<Blueprint> allBluePrints = new HashSet<>(blueprints.values());

        if (allBluePrints.isEmpty()){
            throw new BlueprintNotFoundException("Not found BluePrint from this author.");
        }else {
            return allBluePrints;
        }

    }




}
