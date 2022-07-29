package de.fhws.fiw.fds.exam03.utils.csvConverter;


import de.fhws.fiw.fds.exam02.models.StudyTripReportEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Collection;

public class StudyTripReportEntryCsvConverter
{
	private final CSVPrinter csvPrinter;
	private final Appendable appendable;

	public StudyTripReportEntryCsvConverter(final Appendable appendable ) throws IOException
	{
		this.appendable = appendable;
		this.csvPrinter = new CSVPrinter(
			appendable,
			CSVFormat.DEFAULT.withHeader( "City", "Country", "Number of students", "Number of days") );
	}

	public void write( final StudyTripReportEntry entry )
	{
		try
		{
			this.csvPrinter.printRecord(
					entry.getCity(),
					entry.getCountry(),
					entry.getNumberOfStudents(),
					entry.getNumberOfDays());
			this.csvPrinter.flush( );
		}
		catch ( final IOException e )
		{
			e.printStackTrace( );
		}
	}

	public void write( final Collection<StudyTripReportEntry> entry )
	{
		entry.stream( ).forEach( p -> write( p ) );
	}
}
