package org.batch.trump.writer;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.batch.trump.model.reader.OrganisationModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * Created by BOUGHABA on 22/03/2017.
 */
public class OrganisationOrientItemWriter implements ItemWriter<OrganisationModel> {
    @Autowired
    OrientGraphFactory orientGraphFactory;

    @Override
    public void write(List<? extends OrganisationModel> list) throws Exception {
        OrientGraph graph  = orientGraphFactory.getTx();
        try {
            for(OrganisationModel organisation : list) {
                Vertex firstPersone= null;
                Vertex secondePerson = null;
                Iterable<Vertex> persones = graph.getVertices("TrumpOrganisation.name", organisation.getFirstOrganisation());
                Iterator<Vertex> iterator =  persones.iterator();
                while(iterator.hasNext()){
                    firstPersone = iterator.next();
                    break;
                }
                if(firstPersone == null){
                    firstPersone = graph.addVertex("class:TrumpOrganisation","name", organisation.getFirstOrganisation());
                }

                Iterable<Vertex> sepersones = graph.getVertices("TrumpOrganisation.name", organisation.getSecondeOrganisation());
                Iterator<Vertex> iterator2 =  sepersones.iterator();
                while(iterator2.hasNext()){
                    secondePerson = iterator2.next();
                    break;
                }
                if(secondePerson == null){
                    secondePerson =  graph.addVertex("class:TrumpOrganisation", "name", organisation.getSecondeOrganisation());
                }

                Edge lucaKnowsMarko = graph.addEdge("class:InRelation", firstPersone, secondePerson, "inRelation");
                lucaKnowsMarko.setProperty("type", organisation.getRelationShip());
                lucaKnowsMarko.setProperty("source", organisation.getSource());

            }

        } finally {
            graph.commit();
        }
    }
}
