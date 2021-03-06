/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.rackhack;

import org.junit.Test;
import org.mortbay.jetty.Request;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DelegatingServletTest {
    public String uri = "/go/rails/stuff/action";
    public String attrName;
    public String attrValue;

    @Test
    public void shouldDelegateToTheGivenServlet() throws IOException, ServletException {
        MockServletContext ctx = new MockServletContext();
        ctx.addInitParameter(DelegatingListener.DELEGATE_SERVLET, DummyServlet.class.getCanonicalName());
        ServletContextEvent evt = new ServletContextEvent(ctx);
        DelegatingListener listener = new DelegatingListener();
        listener.contextInitialized(evt);
        assertThat((DummyServlet) ctx.getAttribute(DelegatingListener.DELEGATE_SERVLET), is(DummyServlet.class));
        DelegatingServlet servlet = new DelegatingServlet();
        servlet.init(new MockServletConfig(ctx));
        Request request = new Request() {
            @Override
            public String getRequestURI() {
                return uri;
            }

            @Override
            public void setRequestURI(String newUri) {
                uri = newUri;
            }

            @Override
            public void setAttribute(String name, Object value) {
                attrName = name;
                attrValue = value.toString();
            }
        };
        servlet.service(request, new MockHttpServletResponse());
        assertThat(attrName, is(DummyServlet.URI_ATTRIBUTE));
        assertThat(attrValue, is("/go/stuff/action"));
    }
}
