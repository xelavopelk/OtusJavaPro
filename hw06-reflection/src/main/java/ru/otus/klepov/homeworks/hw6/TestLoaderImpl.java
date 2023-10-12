package ru.otus.klepov.homeworks.hw6;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TestLoaderImpl implements TestLoader {
    private Logger log = Logger.getLogger(TestLoaderImpl.class.getName());
    private TestCaseFactory tcFactory;
    public TestLoaderImpl(TestCaseFactory tcFactory) {
        this.tcFactory = tcFactory;
    }
    public void run() {
        log.info(String.format("Start test %s",tcFactory.getClassName()));
        log.info("Start collect test cases");
        var tc = tcFactory.getTestCases();
        log.info(String.format("Finish collect test cases. Found=%d",tc.size()));
        var success = 0;
        for (var test: tc) {
            try{
                log.info(String.format("Try %s",test.getName()));
                test.run();
                log.info(String.format("Success %s",test.getName()));
                success++;
            }
            catch (Exception ex) {
                log.log(Level.WARNING, String.format("ERROR MESS=%s; StackTrace=%s",ex.getCause().getMessage(),ex.getStackTrace().toString()));
            }
        }
        log.info(String.format("Finish test %s; count=%d, successes=%d, failed=%d",tcFactory.getClassName(), tc.size(),success,tc.size()-success));
    }
}
