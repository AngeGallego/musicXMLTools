package MusicXMLDiff;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MessageFormatter extends Formatter
{
    MessageFormatter() { super(); }

    @Override
    public String format(final LogRecord record)
    {
        return record.getMessage() + System.getProperty("line.separator");
    }
}