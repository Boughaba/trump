package org.batch.trump.repository;

import org.batch.trump.persisence.OrientPerson;
import org.springframework.data.orient.graph.repository.OrientGraphRepository;

/**
 * Created by BOUGHABA on 20/03/2017.
 */
public interface PersonRepository extends OrientGraphRepository<OrientPerson> {
}
