package lt.swedbank.playground.library.sites.manager;

import lt.swedbank.playground.library.reports.KidVisitTracker;
import lt.swedbank.playground.library.sites.Site;
import lt.swedbank.playground.library.visitors.Kid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SiteManagerTest {

    private final static Log LOG = LogFactory.getLog(SiteManagerTest.class);

    @Mock
    private DoubleQueue dqueue;

    @Mock
    private KidVisitTracker tracker;

    @Mock
    private Site site;

    @InjectMocks
    private SiteManager manager = new SiteManager(site);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPopKidFromQueue() {
        Kid kid = new Kid(1, "test", true, 1);

        when(dqueue.removeKidWithoutCounterChange(kid)).thenReturn(true);
        boolean result = manager.popKid(kid);

        assertTrue(result);
        verify(dqueue).removeKidWithoutCounterChange(kid);
    }

    @Test
    public void shouldNotPopAbsentKidl() {
        Kid kid = new Kid(1, "test", true, 1);

        when(dqueue.removeKidWithoutCounterChange(kid)).thenReturn(false);
        boolean result = manager.popKid(kid);

        assertFalse(result);
        verify(dqueue).removeKidWithoutCounterChange(kid);
    }

    @Test
    public void shouldAddKidToPlayground() {
        Kid kid = new Kid(1, "test", true, 1);
        when(site.kidsAllowed()).thenReturn(10L);


        boolean result = manager.addKidToPlaySite(kid, false);

        assertTrue(result);
        verify(tracker).registerKidOnSite(kid, site);
    }

    @Test
    public void shouldAddKidToQueue() {
        Kid kid = new Kid(1, "test", true, 1);
        when(site.kidsAllowed()).thenReturn(-1L);
        when(dqueue.addToQueue(kid)).thenReturn(true);

        boolean result = manager.addKidToPlaySite(kid, false);

        assertTrue(result);
        verify(site).kidsAllowed();
        verify(dqueue).addToQueue(kid);
    }

    @Test
    public void shouldAddToVipKidToQueue() {
        Kid kid = new Kid(1, "test", true, 1);
        when(site.kidsAllowed()).thenReturn(-1L);
        when(dqueue.addToVipQueue(kid)).thenReturn(true);

        boolean result = manager.addKidToPlaySite(kid, true);

        assertTrue(result);
        verify(dqueue).addToVipQueue(kid);
    }

    @Test
    public void shoulNotAddKid() {
        Kid kid = new Kid(1, "test", false, 1);
        when(site.kidsAllowed()).thenReturn(-1L);

        boolean result = manager.addKidToPlaySite(kid, false);

        assertFalse(result);
    }


}
