package org.batch.trump.writer;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.batch.trump.model.reader.PersonOrgModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * Created by BOUGHABA on 23/03/2017.
 */
public class PersonOrgItemWriter implements ItemWriter<PersonOrgModel>{

    @Autowired
    OrientGraphFactory orientGraphFactory;

    @Override
    public void write(List<? extends PersonOrgModel> list) throws Exception {
        OrientGraph graph  = orientGraphFactory.getTx();
        try {
            for(PersonOrgModel orgPerson : list) {
                Vertex persone= null;
                Vertex organisation = null;
                Iterable<Vertex> persones = graph.getVertices("TrumpPerson.name", orgPerson.getPerson());
                Iterator<Vertex> iterator =  persones.iterator();
                while(iterator.hasNext()){
                    persone = iterator.next();
                    break;
                }
                if(persone == null){
                    persone = graph.addVertex("class:TrumpPerson","name", orgPerson.getPerson());
                }

                Iterable<Vertex> sepersones = graph.getVertices("TrumpOrganisation.name", orgPerson.getOrganisation());
                Iterator<Vertex> iterator2 =  sepersones.iterator();
                while(iterator2.hasNext()){
                    organisation = iterator2.next();
                    break;
                }
                if(organisation == null){
                    organisation =  graph.addVertex("class:TrumpOrganisation", "name", orgPerson.getOrganisation());
                }

                Edge lucaKnowsMarko = graph.addEdge("class:HasRole", persone, organisation, "hasRole");
                lucaKnowsMarko.setProperty("type", orgPerson.getRelationShip());
                lucaKnowsMarko.setProperty("source", orgPerson.getSource());

            }

        } finally {
            //graph.shutdown();
            graph.commit();
        }
    }
}
