package org.batch.trump.writer;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.batch.trump.model.reader.PersonModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * Created by BOUGHABA on 20/03/2017.
 */
public class OrientItemWriter implements ItemWriter<PersonModel> {
    @Autowired
    OrientGraphFactory orientGraphFactory;

    @Override
    public void write(List<? extends PersonModel> list) throws Exception {

        OrientGraph graph  = orientGraphFactory.getTx();
        try {
            for(PersonModel person : list) {
                Vertex firstPersone= null;
                Vertex secondePerson = null;
                Iterable<Vertex> persones = graph.getVertices("TrumpPerson.name", person.getFirstPerson());
                Iterator<Vertex> iterator =  persones.iterator();
                while(iterator.hasNext()){
                    firstPersone = iterator.next();
                    break;
                }
                if(firstPersone == null){
                    firstPersone = graph.addVertex("class:TrumpPerson","name", person.getFirstPerson());
                }

                Iterable<Vertex> sepersones = graph.getVertices("TrumpPerson.name", person.getSecondPerson());
                Iterator<Vertex> iterator2 =  sepersones.iterator();
                while(iterator2.hasNext()){
                    secondePerson = iterator2.next();
                    break;
                }
                if(secondePerson == null){
                    secondePerson =  graph.addVertex("class:TrumpPerson", "name", person.getSecondPerson());
                }

                Edge lucaKnowsMarko = graph.addEdge("class:Knows", firstPersone, secondePerson, "knows");
                lucaKnowsMarko.setProperty("type", person.getRelationShip());
                lucaKnowsMarko.setProperty("source", person.getSource());

            }

        } finally {

            graph.commit();
        }
    }
}
