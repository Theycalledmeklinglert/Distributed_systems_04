package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam03.utils.BasicAuthHelper;
import de.fhws.fiw.fds.exam03.utils.BearerAuthHelper;
import de.fhws.fiw.fds.exam03.utils.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "users" )
public class UserService
{
	@Context
	private HttpServletRequest request;

	@GET
	@Path( "me" )
	@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
	public Response getPersons( )
	{
		final User user = BasicAuthHelper.accessControl( request );
		final String token = BearerAuthHelper.createToken( user.getId( ) );

		return Response.ok( token ).build( );
	}
}
