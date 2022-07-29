package utils;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class MyKeyGenerator
{
	private static MyKeyGenerator INSTANCE;

	public static MyKeyGenerator getInstance( )
	{
		if ( INSTANCE == null )
		{
			INSTANCE = new MyKeyGenerator( );
		}

		return INSTANCE;
	}

	private Key key;

	private MyKeyGenerator( )
	{
		try
		{
			final SecureRandom rand = new SecureRandom( );
			final KeyGenerator keyGen = KeyGenerator.getInstance( "AES" );
			keyGen.init( 256, rand );
			this.key = keyGen.generateKey( );
		}
		catch ( final Exception e )
		{
			e.printStackTrace( );
			this.key = null;
		}
	}

	public Key getKey( )
	{
		return this.key;
	}
}
