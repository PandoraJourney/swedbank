package lt.swedbank.playground.library;

import lt.swedbank.playground.library.sites.manager.Manager;
import lt.swedbank.playground.library.visitors.Kid;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SiteControlServiceTest {

    @Mock
    private List<Manager> managers;

    @InjectMocks
    private SiteControlService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = SiteNotFoundException.class)
    public void shouldThrowExceptionWhenTryingToAddToAbsentSite() throws SiteNotFoundException, KidException {
        Manager manager = Mockito.mock(Manager.class);
        Kid kid = new Kid(1, "kid", false, 0);
        when(managers.stream()).thenReturn(Stream.of(manager));
        when(manager.siteName()).thenReturn("test");

        service.addKidToSite("slide", kid, false);
    }

    @Test(expected = SiteNotFoundException.class)
    public void shouldThrowExceptionWhenTryingToPopToAbsentSite() throws SiteNotFoundException, KidException {
        Manager manager = Mockito.mock(Manager.class);
        Kid kid = new Kid(1, "kid", false, 0);
        when(managers.stream()).thenReturn(Stream.of(manager));
        when(manager.siteName()).thenReturn("test");

        service.popKidFromSite("slide", kid);
    }

    @Test(expected = KidException.class)
    public void shouldThrowExceptionWhenTryingToPopAbsentKid() throws SiteNotFoundException, KidException {
        Manager manager = Mockito.mock(Manager.class);
        Kid kid = new Kid(1, "kid", false, 0);
        when(managers.stream()).thenReturn(Stream.of(manager));
        when(manager.siteName()).thenReturn("slide");
        when(manager.containsKid(any())).thenReturn(false);

        service.popKidFromSite("slide", kid);
    }
}
