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

package com.thoughtworks.go.helper;

import com.thoughtworks.go.config.CaseInsensitiveString;
import com.thoughtworks.go.config.EnvironmentConfig;
import com.thoughtworks.go.config.EnvironmentsConfig;

public class EnvironmentConfigMother {
    public static final String OMNIPRESENT_AGENT = "omnipresent-agent";

    public static EnvironmentsConfig environments(String... names) {
        EnvironmentsConfig environmentsConfig = new EnvironmentsConfig();
        for (String name : names) {
            environmentsConfig.add(environment(name));
        }
        return environmentsConfig;
    }

    private static EnvironmentConfig env(String name) {
        return new EnvironmentConfig(new CaseInsensitiveString(name));
    }

    public static EnvironmentConfig environment(String name) {
        EnvironmentConfig uat = new EnvironmentConfig(new CaseInsensitiveString(name));
        uat.addPipeline(new CaseInsensitiveString(name + "-pipeline"));
        uat.addAgent(name + "-agent");
        uat.addAgent(OMNIPRESENT_AGENT);
        return uat;
    }

    public static EnvironmentConfig environment(String name, String... pipelines) {
        EnvironmentConfig config = env(name);
        for (String pipeline : pipelines) {
            config.addPipeline(new CaseInsensitiveString(pipeline));
        }
        return config; 
    }
}
