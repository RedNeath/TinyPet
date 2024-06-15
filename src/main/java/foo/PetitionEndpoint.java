package foo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.Transaction;

@Api(
	name = "tinyPetApi",
	version = "ca-marche-sur-mon-pc",
	audiences = "927375242383-t21v9ml38tkh2pr30m4hqiflkl3jfohl.apps.googleusercontent.com",
	clientIds = {
		"927375242383-t21v9ml38tkh2pr30m4hqiflkl3jfohl.apps.googleusercontent.com",
        "927375242383-jm45ei76rdsfv7tmjv58tcsjjpvgkdje.apps.googleusercontent.com"
	},
	namespace = @ApiNamespace(
		ownerDomain = "helloworld.example.com",
		ownerName = "helloworld.example.com",
		packagePath = ""
	)
)
public class PetitionEndpoint {
	@ApiMethod(name="createPetition", httpMethod=HttpMethod.POST)
	public Entity createPetition(PetitionCreate petitionCreate, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 6);
		Date endDate = cal.getTime();
		String email = request.getHeader("Email");

		Entity petition = new Entity("Petition");
		petition.setProperty("author", email);
		petition.setProperty("name", petitionCreate.name);
		petition.setProperty("description", petitionCreate.description);
		petition.setProperty("signature_target", petitionCreate.signatureTarget);
		petition.setProperty("beg_date", new Date());
		petition.setProperty("end_date", endDate);
		petition.setProperty("tags", petitionCreate.tags);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		datastoreService.put(petition);
		transaction.commit();

		return petition;
	}
	
	@ApiMethod(name="recentPetitions", httpMethod=HttpMethod.GET)
	public List<Entity> recentPetitions() {
		Query q = new Query("Petition").addSort("beg_date", SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> petitions = pq.asList(FetchOptions.Builder.withLimit(100));
		return petitions;
	}

	@ApiMethod(name="petitionsWithTags", httpMethod=HttpMethod.GET)
	public List<Entity> petitionsWithTags(HttpServletRequest request) throws BadRequestException {
		if (request.getParameter("tags") == null) throw new BadRequestException("Not enough tags", "Must have at least one tag");
		List<String> parsedTags = new ArrayList<>(Arrays.asList(request.getParameter("tags").split(" ")));
		Query q = new Query("Petition")
			.setFilter(new FilterPredicate("tags", FilterOperator.IN, parsedTags))
			.addSort("beg_date", SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		
		return pq.asList(FetchOptions.Builder.withLimit(100));
	}

	@ApiMethod(name="petitionsSignedByUser", httpMethod=HttpMethod.GET)
	public List<Entity> petitionsSignedByUser(@Named("username") String username) {
		Query q = new Query("Signature")
			.setFilter(new FilterPredicate("user", FilterOperator.EQUAL, username))
			.addSort("date", SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> signatures = pq.asList(FetchOptions.Builder.withDefaults());

		if (signatures.isEmpty()) {
			return new ArrayList<>();
		}
		List<Key> petitionIds = signatures.stream()
			.map(signature -> KeyFactory.createKey("Petition", (Long) signature.getProperty("petition")))
			.collect(Collectors.toList());

		Query r = new Query("Petition")
			.setFilter(new FilterPredicate("__key__", FilterOperator.IN, petitionIds));
		
		PreparedQuery pr = datastore.prepare(r);
		return pr.asList(FetchOptions.Builder.withDefaults());
	}

	@ApiMethod(name="petitionSignatories", httpMethod=HttpMethod.GET)
	public List<Entity> petitionSignatories(@Named("petition") long petition) {
		Query q = new Query("Signature")
			.setFilter(new FilterPredicate("petition", FilterOperator.EQUAL, petition));
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);

		return pq.asList(FetchOptions.Builder.withDefaults());
	}

	@ApiMethod(name="signPetition", httpMethod=HttpMethod.POST)
	public Entity signPetition(@Named("petition") long petition, HttpServletRequest request) {
		// Has to be connected via google!
		// Payload payload = ...
		String email = request.getHeader("Email");

		Entity signature = new Entity("Signature");
		signature.setProperty("date", new Date());
		signature.setProperty("petition", petition);
		// signature.setProperty("user", payload.getEmail());
		signature.setProperty("user", email);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		datastoreService.put(signature);
		transaction.commit();

		return signature;
	}
}
