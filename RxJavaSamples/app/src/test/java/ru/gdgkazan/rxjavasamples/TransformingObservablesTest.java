package ru.gdgkazan.rxjavasamples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TransformingObservablesTest {

    @Test
    public void testSequential() throws Exception {
        assertNotNull(TransformingObservables.sequential());
    }

    @Test
    public void testParallel() throws Exception {
        assertNotNull(TransformingObservables.parallel());
    }

    @Test
    public void testZip() throws Exception {
        assertNotNull(TransformingObservables.zipPersons());
    }


}
