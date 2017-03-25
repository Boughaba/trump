package org.batch.trump.processor;

import org.batch.trump.model.reader.PersonOrgModel;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by BOUGHABA on 23/03/2017.
 */
public class PersonOrgItemProcessor implements ItemProcessor<PersonOrgModel,PersonOrgModel> {

    @Override
    public PersonOrgModel process(PersonOrgModel personOrgModel) throws Exception {
        return personOrgModel;
    }
}
