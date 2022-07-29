package de.fhws.fiw.fds.exam02.client.rest.resources;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.exam02.tests.models.EmptyResource;
import de.fhws.fiw.fds.exam02.tests.util.headers.HeaderMap;

import java.util.List;

public class EmptyResourceRestClient extends AbstractResourceRestClient<EmptyResource>
{
	public EmptyResourceRestClient(String userName, String password, final HeaderMap headers )
	{
		super(userName, password, headers );
	}

	@Override protected String defineUrl( )
	{
		return "";
	}

	@Override protected Class<EmptyResource> defineClassTypeForSingleResource( )
	{
		return EmptyResource.class;
	}

	@Override protected GenericType<List<EmptyResource>> defineClassTypeForCollectionResource( )
	{
		return new GenericType<List<EmptyResource>>( ) { };
	}
}