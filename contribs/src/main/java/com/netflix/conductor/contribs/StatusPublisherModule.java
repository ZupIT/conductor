package com.netflix.conductor.contribs;

import com.google.inject.AbstractModule;
import com.netflix.conductor.contribs.listener.WorkflowStatusPublisher;
import com.netflix.conductor.core.execution.WorkflowStatusListener;

public class StatusPublisherModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WorkflowStatusListener.class).to(WorkflowStatusPublisher.class);
    }
}
