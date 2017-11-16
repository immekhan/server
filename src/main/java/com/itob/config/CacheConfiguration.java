package com.itob.config;

import com.itob.domain.*;
import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.itob.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.itob.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.itob.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.itob.domain.AbsUser.class.getName() , jcacheConfiguration);
            cm.createCache(UserAddress.class.getName() , jcacheConfiguration);
            cm.createCache(UserCredentials.class.getName() , jcacheConfiguration);
            cm.createCache(UserTypeCredentialPolicy.class.getName() , jcacheConfiguration);
            cm.createCache(UserIdentification.class.getName() , jcacheConfiguration);
            cm.createCache(UserPrivileges.class.getName() , jcacheConfiguration);
            cm.createCache(UserRoles.class.getName() , jcacheConfiguration);
            cm.createCache(UserType.class.getName() , jcacheConfiguration);
            cm.createCache(SessionPolicy.class.getName() , jcacheConfiguration);
            cm.createCache(IdentificationType.class.getName() , jcacheConfiguration);
            cm.createCache(AddressType.class.getName() , jcacheConfiguration);
            cm.createCache(RolePrivilege.class.getName() , jcacheConfiguration);
            cm.createCache(CredentialStatus.class.getName() , jcacheConfiguration);
            cm.createCache(CredentialType.class.getName() , jcacheConfiguration);
            cm.createCache(OrgUnit.class.getName() , jcacheConfiguration);
            cm.createCache(AddressStatus.class.getName() , jcacheConfiguration);
            cm.createCache(Privileges.class.getName() , jcacheConfiguration);
            cm.createCache(Roles.class.getName() , jcacheConfiguration);
            cm.createCache(BlackListReason.class.getName() , jcacheConfiguration);
            cm.createCache(CredentialPolicy.class.getName() , jcacheConfiguration);
            cm.createCache(com.itob.domain.AbsAbstractAuditingEntity.class.getName() , jcacheConfiguration);
            cm.createCache(City.class.getName() , jcacheConfiguration);
            cm.createCache(Country.class.getName() , jcacheConfiguration);
            cm.createCache(ErrorCode.class.getName() , jcacheConfiguration);
            cm.createCache(Session.class.getName() , jcacheConfiguration);
            cm.createCache(Company.class.getName() , jcacheConfiguration);
            cm.createCache(CompanyDeviceType.class.getName() , jcacheConfiguration);
            cm.createCache(CompanyType.class.getName() , jcacheConfiguration);
            cm.createCache(DeviceType.class.getName() , jcacheConfiguration);
            cm.createCache(CompanyDeviceTypeCredential.class.getName() , jcacheConfiguration);
            cm.createCache(FileType.class.getName(), jcacheConfiguration);
            cm.createCache(DummyForm.class.getName(), jcacheConfiguration);
            // needle-ehcache-add-entry
        };
    }
}
