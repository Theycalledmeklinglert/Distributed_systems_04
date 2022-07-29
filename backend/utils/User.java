package utils;

public class User
{
	private String id;

	private String secret;

	private String role;

	public User( )
	{
	}

	public User( final String id, final String secret )
	{
		this.id = id;
		this.secret = secret;
		this.role = "user";
	}

	public User( final String id, final String secret, final String role )
	{
		this.id = id;
		this.secret = secret;
		this.role = role;
	}

	public String getId( )
	{
		return id;
	}

	public String getSecret( )
	{
		return secret;
	}

	public String getRole( )
	{
		return role;
	}

	public User cloneWithoutSecret( )
	{
		final User returnValue = new User( );

		returnValue.id = this.id;
		returnValue.role = this.role;

		return returnValue;
	}
}
