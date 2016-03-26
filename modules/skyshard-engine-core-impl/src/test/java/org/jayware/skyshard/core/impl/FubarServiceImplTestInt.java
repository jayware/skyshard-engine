/**
 * F.A.C.T Client -- Client application of F.A.C.T
 *
 * Copyright (C) 2016 Markus Neubauer <markus.neubauer@jayware.org>,
 *                    Alexander Haumann <alexander.haumann@jayware.org>,
 *                    Manuel Hinke <manuel.hinke@jayware.org>,
 *                    Marina Schilling <marina.schilling@jayware.org>,
 *                    Elmar Schug <elmar.schug@jayware.org>,
 *
 *     This file is part of F.A.C.T Client.
 *
 *     F.A.C.T Client is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     F.A.C.T Client is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jayware.skyshard.core.impl;


import org.jayware.skyshard.core.api.Fubar;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.exam.CoreOptions.systemPackages;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;
import static org.osgi.framework.Constants.BUNDLE_SYMBOLICNAME;
import static org.osgi.framework.Constants.EXPORT_PACKAGE;


//@Listeners(PaxExam.class)
//@ExamReactorStrategy(PerMethod.class)
public class FubarServiceImplTestInt
{
//    @Inject
    private Fubar testee;

//    @Configuration
    public Option[] config() {

        return options(
            systemPackages("org.assertj.core.api"),
            mavenBundle("org.apache.felix", "org.apache.felix.scr", "2.0.2"),
            mavenBundle("org.slf4j", "slf4j-api", "1.7.18"),
            mavenBundle("ch.qos.logback", "logback-core", "1.1.6"),
            mavenBundle("ch.qos.logback", "logback-classic", "1.1.6"),
            provision(bundle()
                .add(Fubar.class)
                .add(FubarServiceImpl.class)
                .add("FubarService.xml", getClass().getResource("/FubarService.xml"))
                .set(BUNDLE_SYMBOLICNAME, "test")
                .set(EXPORT_PACKAGE, "org.jayware.fact.engine.core.api,org.jayware.fact.engine.core.impl")
                .set("Service-Component", "FubarService.xml")
                .build(withBnd())
            )
        );
    }

//    @ProbeBuilder
    public TestProbeBuilder probe(TestProbeBuilder builder)
    {
        return builder;
    }

//    @Test
    public void getHelloService() {
//        assertThat(testee).isNotNull();
//        assertThat(testee.hello()).isEqualTo("Hello World");
    }
}
