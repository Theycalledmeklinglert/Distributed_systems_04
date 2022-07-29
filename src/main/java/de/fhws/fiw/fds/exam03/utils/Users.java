package de.fhws.fiw.fds.exam03.utils;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Users
{
	private static Users INSTANCE;

	public static Users getInstance( )
	{
		if ( INSTANCE == null )
		{
			INSTANCE = new Users( );
		}

		return INSTANCE;
	}

	private final Map<String, User> mapIdToUser;

	private Users( )
	{
		this.mapIdToUser = new HashMap<>( );
		createUsers( );
	}

	public void addUser( final String id, final String password, final String role )
	{
		this.mapIdToUser.put( id, new User( id, password, role ) );
	}

	public User accessControl( final String id, final String... roles )
	{
		if ( this.mapIdToUser.containsKey( id ) == false )
		{
			throw new NotAuthorizedException( "" );
		}
		else
		{
			final User user = this.mapIdToUser.get( id );
			checkRoles( user, roles );
			return user.cloneWithoutSecret( );
		}
	}

	public User accessControl( final String id, final String password, final String... roles )
	{
		if ( this.mapIdToUser.containsKey( id ) == false )
		{
			throw new NotAuthorizedException( "" );
		}
		else if ( this.mapIdToUser.get( id ).getSecret( ).equals( password ) )
		{
			final User user = this.mapIdToUser.get( id );
			checkRoles( user, roles );
			return user.cloneWithoutSecret( );
		}
		else
		{
			throw new NotAuthorizedException( "" );
		}
	}

	private void checkRoles( final User user, final String... roles )
	{
		if ( roles.length > 0 )
		{
			if ( Arrays.stream( roles ).noneMatch( role -> user.getRole( ).equalsIgnoreCase( role ) ) )
			{
				throw new ForbiddenException( "" );
			}
		}
	}

	private void createUsers( )
	{
		addUser( "Admin", "secret", "admin" );
		addUser( "Lecturer", "secret", "lecturer" );
	}
}
