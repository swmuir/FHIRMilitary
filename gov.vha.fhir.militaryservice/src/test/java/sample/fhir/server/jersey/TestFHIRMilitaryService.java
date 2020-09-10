package sample.fhir.server.jersey;

import java.io.IOException;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.StringClientParam;
import ca.uhn.fhir.rest.gclient.TokenClientParam;

 
public class TestFHIRMilitaryService {

	@Test
	public void runPatientSearch() throws IOException {
		
		// Create a client (only needed once)
		FhirContext ctx =  FhirContext.forR4();
		IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4");

		// Invoke the client
		Bundle bundle = (Bundle) client.search().forResource(Patient.class)
		.where(new StringClientParam("family").matches().value("Share"))
		.where(new StringClientParam("given").matches().value("Sam"))
		.where(new TokenClientParam("identifier").exactly().systemAndCode("urn:oid:1.1.1.1.1", "1032702"))
		.prettyPrint()
		.execute();
		
		
		IParser parser = ctx.newJsonParser();
		parser.setPrettyPrint(true);

		// Serialize it
		String serialized = parser.encodeResourceToString(bundle);
		System.out.println(serialized);

		
//		System.out.println(bundle);
	}

}
