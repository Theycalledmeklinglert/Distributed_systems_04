package de.fhws.fiw.fds.exam02.tests.student;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.tests.models.Student;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DeleteStudentTest extends AbstractStudentTest
{
	public final static String GET_ALL_STUDENTS = "getAllStudents";

	@Test
	public void test_204_and_404( ) throws IOException
	{
		//post new resource
		final RestApiResponse<Student> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), userName, password );

		System.out.println(responseFromPostRequest.getLastStatusCode());

		final String locationHeader = responseFromPostRequest.getLocationHeader( );

		//get new resource
		final RestApiResponse<Student> responseFromFirstGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, userName, password );

		final Student studentToDelete = responseFromFirstGetRequest.getResponseSingleData( );

		assertNotNull( studentToDelete );

		//delete new resource
		final RestApiResponse<Student> responseFromDeleteRequest = deleteRequest( HeaderMapUtils.empty( ), studentToDelete, userName, password );

		assertEquals( 204, responseFromDeleteRequest.getLastStatusCode( ) );

		//try to get deleted resource
		final RestApiResponse<Student> responseFromSecondGetRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, userName, password );

		assertEquals( 404, responseFromSecondGetRequest.getLastStatusCode( ) );

		final Student studentFromSecondGetRequest = responseFromSecondGetRequest.getResponseSingleData( );

		assertNull( studentFromSecondGetRequest );
	}

	private Student defineNewResource( )
	{
		return new Student(
			"Patrick",
			"Müller",
			"patrick.mueller@fhws.de",
			"BIN",
			5,
			1234 );
	}

	@Test
	public void test_hypermedia( ) throws IOException
	{
		final RestApiResponse<Student> response = postAndDeleteRequest( );

		assertLinkHeaderEquals( response, GET_ALL_STUDENTS, BASE_URL + "students" );
	}

	protected RestApiResponse<Student> postAndDeleteRequest( ) throws IOException
	{
		final RestApiResponse<Student> responseFromPostRequest = postRequest( HeaderMapUtils.withContentTypeJson( ), defineNewResource( ), userName, password );

		final String locationHeader = responseFromPostRequest.getLocationHeader( );

		final RestApiResponse<Student> responseFromFirstRequest = getSingleRequestByUrl( HeaderMapUtils.withAcceptJson( ), locationHeader, userName, password );

		final Student studentToDelete = responseFromFirstRequest.getResponseSingleData( );

		assertNotNull( studentToDelete );

		return deleteRequest( HeaderMapUtils.empty( ), studentToDelete, userName, password );
	}
}