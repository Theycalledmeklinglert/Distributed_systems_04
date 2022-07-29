package de.fhws.fiw.fds.exam02.tests;

import de.fhws.fiw.fds.exam02.client.rest.RestApiResponse;
import de.fhws.fiw.fds.exam02.client.rest.resources.AbstractResourceRestClient;
import de.fhws.fiw.fds.exam02.tests.models.AbstractModel;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMapUtils;

import java.io.IOException;

public abstract class AbstractSubResourceTest<R extends AbstractModel, C extends AbstractResourceRestClient<R>> extends
	AbstractTest<R, C>
{
	protected RestApiResponse<R> linkSecondaryResourceById(
		final long id, String userName, String password )
		throws IOException
	{
		final R secondaryResource = loadSecondaryResourceById( HeaderMapUtils.withAcceptJson( ), id, userName, password );

		return linkSecondaryResource(
			HeaderMapUtils.withContentTypeJson( ),
			secondaryResource,
			defineBaseUrl( ) + "/" + secondaryResource.getId( ), userName, password );
	}

	protected R loadSecondaryResourceById(
		final HeaderMap headers,
		final long idOfSecondaryResource, String userName, String password )
		throws IOException
	{
		return getSingleRequestById( headers, idOfSecondaryResource, userName, password ).getResponseSingleData( );
	}

	protected RestApiResponse<R> linkSecondaryResource(
		final HeaderMap headers,
		final R resourceToBeLinked,
		final String url, String userName, String password )
		throws IOException
	{
		return putRequestByUrl( headers, resourceToBeLinked, url, userName, password);
	}

	protected RestApiResponse<R> unlinkSecondaryResource(
		final HeaderMap headers,
		final String url, String userName, String password )
		throws IOException
	{
		return deleteRequestByUrl( headers, url, userName, password );
	}
}