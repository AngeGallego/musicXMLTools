package MusicXMLDiff;

import java.io.OutputStream;
import java.util.Stack;
import java.util.logging.*;

class DiffLogger {

    private Level mLogLevel = Level.INFO;
    private Logger mLogger = Logger.getLogger(DiffLogger.class.getName());
    private Stack<String> mMessageStack = new Stack<>();

    DiffLogger() {
        this(System.out);
    }

    DiffLogger(OutputStream os) {
        mLogger.setUseParentHandlers(false);
        for (Handler handler : mLogger.getHandlers())
            mLogger.removeHandler(handler);
        StreamHandler streamHandler = new StreamHandler(os, new MessageFormatter()) {
            @Override
            public synchronized void publish(final LogRecord record) {
                super.publish(record);
                flush();
            }
        };
        streamHandler.setLevel(Level.ALL);
        mLogger.addHandler(streamHandler);
    }

    void log(String message) {
        mLogger.log(mLogLevel, message);
    }

    void stackMessage(String message) {
        mMessageStack.push(message);
    }

    void popMessage() {
        if (!mMessageStack.empty()) {
            mLogger.log(mLogLevel, mMessageStack.pop());
        }
    }

    void popAllMessages() {
        while (!mMessageStack.isEmpty())
            mLogger.log(mLogLevel, mMessageStack.pop());
    }
}
