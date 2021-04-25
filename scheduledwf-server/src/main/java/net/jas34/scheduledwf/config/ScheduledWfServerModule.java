package net.jas34.scheduledwf.config;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.AbstractModule;
import com.netflix.conductor.core.config.Configuration;

import net.jas34.scheduledwf.resources.ScheduleWfResource;
import net.jas34.scheduledwf.resources.SchedulerResource;

/**
 * @author Jasbir Singh
 */
public class ScheduledWfServerModule extends AbstractModule {
    @Override
    protected void configure() {
        String database = StringUtils.isEmpty(System.getProperty(Configuration.DB_PROPERTY_NAME))
                ? Configuration.DB_DEFAULT_VALUE.toUpperCase()
                : System.getProperty(Configuration.DB_PROPERTY_NAME).toUpperCase();

        if(Configuration.DB.MEMORY.name().equals(database)) {
            install(new InMemoryPersistenceModule());

        }else if(Configuration.DB.MYSQL.name().equals(database)) {
            install(new MySQLPersistenceModule());
        }else {
            throw new UnsupportedOperationException("Only MEMORY AND MYSQL persistence supported for scheduled workflow module.");
        }

        install(new ScheduledWorkflowsModule());
        bind(ScheduleWfResource.class);
        bind(SchedulerResource.class);
    }
}
