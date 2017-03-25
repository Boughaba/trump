package org.batch.trump.processor;

import org.batch.trump.model.reader.PersonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<PersonModel, PersonModel> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public PersonModel process(final PersonModel person) throws Exception {

  /*      List objectToWrite =  new ArrayList();
        OrientPerson firstPerson = new OrientPerson();
        OrientPerson secondPerson = new OrientPerson();
        PersonRelationShip relation = new PersonRelationShip();
        secondPerson.setName(person.getSecondPerson());
        relation.setRelationType(person.getRelationShip());
        relation.setSource(person.getSource());
        firstPerson.setName(person.getFirstPerson());

        objectToWrite.add(firstPerson);
        objectToWrite.add(secondPerson);
        objectToWrite.add(relation);


*/
        return person;
    }

}
