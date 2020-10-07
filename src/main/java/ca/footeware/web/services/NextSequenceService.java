package ca.footeware.web.services;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author Footeware.ca
 *
 */
@Service
public class NextSequenceService {
	@Autowired
	private MongoOperations mongo;

	/**
	 * @param seqName {@link String}
	 * @return String
	 * @throws ServiceNotFoundException if shit goes south
	 */
	public String getNextSequence(String seqName) throws ServiceNotFoundException {
		CustomSequences counter = mongo.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
				options().returnNew(true).upsert(true), CustomSequences.class);
		if (counter != null) {
			return String.valueOf(counter.getSeq());
		}
		throw new ServiceNotFoundException("Counter is null");
	}
}