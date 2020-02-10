package lt.swedbank.playground.library;

import lt.swedbank.playground.library.sites.manager.Manager;
import lt.swedbank.playground.library.visitors.Kid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteControlService {
    private static final Log LOG = LogFactory.getLog(SiteControlService.class);

    @Autowired
    private List<Manager> sites;

    public boolean addKidToSite(String siteName, Kid kid, boolean useVip) throws SiteNotFoundException, KidException {
        Manager site = findSiteByName(siteName)
                .orElseThrow(() -> new SiteNotFoundException(siteName));
        if (sites.stream().anyMatch(s -> s.containsKid(kid))) {
            throw new KidException("Kid is already playing. Pop kid first to put him to site");
        }

        return site.addKidToPlaySite(kid, useVip);
    }

    public Kid popKidFromSite(String siteName, Kid kid) throws SiteNotFoundException, KidException {
        Manager site = findSiteByName(siteName)
                .orElseThrow(() -> new SiteNotFoundException(siteName));
        if (!site.popKid(kid)) {
            throw new KidException(kid.getName() + "not found on site" + siteName + ". Should we call the police?");
        }
        return kid;
    }

    private Optional<Manager> findSiteByName(String name) {
        return sites.stream()
                    .filter(site -> site.siteName().equals(name))
                    .findFirst();
    }
}
