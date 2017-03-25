package org.batch.trump.processor;

import org.batch.trump.model.reader.OrganisationModel;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by BOUGHABA on 22/03/2017.
 */
public class OrganisationItemProcessor implements ItemProcessor<OrganisationModel, OrganisationModel> {

    @Override
    public OrganisationModel process(OrganisationModel organisationModel) throws Exception {
        return organisationModel;
    }
}
