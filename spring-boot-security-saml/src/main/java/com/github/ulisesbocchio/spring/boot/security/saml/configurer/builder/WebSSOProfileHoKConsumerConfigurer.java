package com.github.ulisesbocchio.spring.boot.security.saml.configurer.builder;

import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilder;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilderResult;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl;

/**
 * Builder configurer that takes care of configuring/customizing the {@link WebSSOProfileConsumerHoKImpl} bean.
 * <p>
 * Common strategy across most internal configurers is to first give priority to a Spring Bean if present in the
 * Context.
 * So if not {@link WebSSOProfileConsumerHoKImpl} bean is defined, priority goes to a custom
 * WebSSOProfileConsumerHoKImpl provided explicitly to this configurer through the constructor. And if not provided
 * through the constructor, a default implementation is instantiated.
 * </p>
 *
 * @author Ulises Bocchio
 */
public class WebSSOProfileHoKConsumerConfigurer extends SecurityConfigurerAdapter<ServiceProviderBuilderResult, ServiceProviderBuilder> {
    private WebSSOProfileConsumerHoKImpl hokProfileConsumer;
    private WebSSOProfileConsumerHoKImpl hokProfileConsumerBean;

    public WebSSOProfileHoKConsumerConfigurer() {

    }

    public WebSSOProfileHoKConsumerConfigurer(WebSSOProfileConsumerHoKImpl hokProfileConsumer) {
        this.hokProfileConsumer = hokProfileConsumer;
    }

    @Override
    public void init(ServiceProviderBuilder builder) throws Exception {
        hokProfileConsumerBean = builder.getSharedObject(WebSSOProfileConsumerHoKImpl.class);
    }

    @Override
    public void configure(ServiceProviderBuilder builder) throws Exception {
        if (hokProfileConsumerBean == null) {
            if (hokProfileConsumer == null) {
                hokProfileConsumer = createDefaultWebSSOProfileConsumerHoK();
            }
            builder.setSharedObject(WebSSOProfileConsumerHoKImpl.class, hokProfileConsumer);
        }
    }

    @VisibleForTesting
    protected WebSSOProfileConsumerHoKImpl createDefaultWebSSOProfileConsumerHoK() {
        return new WebSSOProfileConsumerHoKImpl();
    }
}
