package foo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import com.google.appengine.repackaged.com.google.datastore.v1.CompositeFilter;
import com.google.appengine.repackaged.com.google.datastore.v1.Projection;
import com.google.appengine.repackaged.com.google.datastore.v1.PropertyFilter;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
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
public class PetitionQuery {
	@ApiMethod(name="create-petition", httpMethod=HttpMethod.POST)
	public Entity createPetition(PetitionCreate petitionCreate) {
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 6);
		Date endDate = cal.getTime();

		Entity petition = new Entity("Petition");
		petition.setProperty("author", petitionCreate.author);
		petition.setProperty("name", petitionCreate.name);
		petition.setProperty("description", petitionCreate.description);
		petition.setProperty("signature_target", petitionCreate.signatureTarget);
		petition.setProperty("beg_date", new Date());
		petition.setProperty("end_date", endDate);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		datastoreService.put(petition);
		transaction.commit();

		return petition;
	}


	/* @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");


		response.getWriter().print("<h2> finall 5 PU where key > P0 </h2>");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("PU", "P0");

		Query q = new Query("PU").setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, k));

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(5));

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		Entity last=null;
		for (Entity entity : result) {
			response.getWriter().print("<li>" + entity.getKey());
			last=entity;
		}

		response.getWriter().print("<h2> Great, get the next 10 results now </h2>");

		
		// One way to paginate...
		q = new Query("PU").setFilter(new FilterPredicate("__key__", FilterOperator.GREATER_THAN, last.getKey()));

		pq = datastore.prepare(q);
		result = pq.asList(FetchOptions.Builder.withLimit(10));

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		last=null;
		for (Entity entity : result) {
			response.getWriter().print("<li>" + entity.getKey());
			last=entity;
		}

		
	} */
}
